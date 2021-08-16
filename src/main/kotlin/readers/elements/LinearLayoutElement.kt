package readers.elements

import generators.nodes.LinearLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import readers.tags.ViewTags

class LinearLayoutElement(
    private val layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<LinearLayoutNode>(layoutElement) {

    override fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = getViewIdNameTag(),
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

                    val totalNodeList: MutableList<Element> = mutableListOf()

                    init {
                        ViewTags.values().map {
                            layoutElement.getElementsByTagName(it.value)
                        }.forEach { writeInList(it) }
                    }

                    override fun next(): ViewNode {
                        val element = totalNodeList[currentIndex]
                        currentIndex++

                        return ViewTags.fromString(element.nodeName).toLayoutElement(element, node()).node()
                    }

                    private fun writeInList(nodeList: NodeList) {
                        for (i in 0 until nodeList.length) {
                            val element = nodeList.item(i) as Element
                            if (element.parentNode == layoutElement)
                                totalNodeList.add(element)
                        }
                    }
                }
            }
        }
    }


}