package readers

import generators.nodes.LinearLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import org.w3c.dom.NodeList

class LinearLayoutReader(private val document: LayoutElement) {

    fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = document.getViewIdNameTag(),
                orientation = getOrientation(document)
            ),
            children = children()
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
        return object : Iterator<ViewNode>, Iterable<ViewNode> {

            private var currentIndex = 0

            override fun hasNext(): Boolean = currentIndex < document.getElementsByTagName("TextView").length

            override fun next(): ViewNode {
                val list: NodeList = document.getElementsByTagName("TextView")
                val element = list.item(currentIndex) as Element
                currentIndex++
                return TextViewReader(LayoutElement(element)).node()
            }

            override fun iterator(): Iterator<ViewNode> {
                return this
            }

        }
    }


}