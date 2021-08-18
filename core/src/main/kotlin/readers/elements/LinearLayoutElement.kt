package readers.elements

import generators.nodes.LinearLayoutNode
import generators.nodes.ViewNode
import generators.nodes.attributes.colors.ColorAttributeParser
import org.w3c.dom.Element

class LinearLayoutElement(
    private val layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<LinearLayoutNode>(layoutElement) {

    private val colorAttributeParser = ColorAttributeParser()

    override fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = getViewIdNameTag(),
                orientation = getOrientation(layoutElement),
                alignment = getAlignmentFromGravity(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background"))
            ),
            children = children(),
            _parent = parentNode
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
                return ViewGroupIterator(this@LinearLayoutElement)
            }
        }
    }


}