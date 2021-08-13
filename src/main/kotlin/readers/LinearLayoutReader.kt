package readers

import generators.nodes.LinearLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import org.w3c.dom.NodeList

class LinearLayoutReader(
    private val layoutElement: LayoutElement,
    private val parentNode: ViewNode?
) {

    fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = layoutElement.getViewIdNameTag(),
                orientation = getOrientation(layoutElement)
            ),
            children = children(),
            parent = parentNode
        )
    }

    private fun getOrientation(documentElement: Element): LinearLayoutNode.Orientation {
        val orientation = documentElement.getAttribute("android:orientation")
        return if (orientation == "vertical")
            LinearLayoutNode.Orientation.Vertical
        else if (orientation == "horizontal" || orientation.isEmpty())
            LinearLayoutNode.Orientation.Horizontal
        else
            throw IllegalStateException("Invalid orientation type: $orientation")
    }

    private fun children(): Iterable<ViewNode> {
        return object : Iterable<ViewNode> {

            override fun iterator(): Iterator<ViewNode> {
                return object : Iterator<ViewNode> {
                    override fun hasNext(): Boolean {
                        return currentIndex < totalNodeList.size
                    }

                    private var currentIndex = 0

                    val textViews = layoutElement.getElementsByTagName("TextView")
                    val linearLayouts = layoutElement.getElementsByTagName("LinearLayout")
                    val totalNodeList: MutableList<Element> = mutableListOf()

                    init {
                        writeInList(textViews)
                        writeInList(linearLayouts)
                    }

                    override fun next(): ViewNode {
                        val element = totalNodeList[currentIndex]
                        currentIndex++
                        return if (element.nodeName == "TextView")
                            TextViewReader(LayoutElement(element), node()).node()
                        else
                            LinearLayoutReader(LayoutElement(element), node()).node()
                    }

                    private fun writeInList(nodeList: NodeList) {
                        for (i in 0 until nodeList.length) {
                            val element = nodeList.item(i) as Element
                            if (element.parentNode == layoutElement.element)
                                totalNodeList.add(element)
                        }
                    }
                }
            }
        }
    }


}