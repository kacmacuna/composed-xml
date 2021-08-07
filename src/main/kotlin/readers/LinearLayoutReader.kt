package readers

import generators.nodes.LinearLayoutNode
import generators.nodes.TextViewNode
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.lang.IllegalStateException

class LinearLayoutReader(private val document: LayoutDocument) {

    fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = document.getViewIdNameTag(document.documentElement),
                orientation = getOrientation(document.documentElement)
            )
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


}