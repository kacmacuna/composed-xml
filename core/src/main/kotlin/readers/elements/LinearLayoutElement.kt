package readers.elements

import generators.nodes.LinearLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class LinearLayoutElement(
    private val layoutElement: Element
) : LayoutElement<LinearLayoutNode>(layoutElement) {


    override fun node(): LinearLayoutNode {
        return LinearLayoutNode(
            LinearLayoutNode.Info(
                id = getViewIdNameTag(),
                orientation = getOrientation(),
                arrangement = getArrangement(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
                weight = getAttribute("android:weight").ifEmpty { "-1" }.toFloat()
            ),
            children = children()
        )
    }

    private fun getOrientation(): LinearLayoutNode.Orientation {
        val orientation = layoutElement.getAttribute("android:orientation")
        return if (orientation == "vertical")
            LinearLayoutNode.Orientation.Vertical
        else if (orientation == "horizontal" || orientation.isEmpty())
            LinearLayoutNode.Orientation.Horizontal
        else
            throw IllegalStateException("Invalid orientation type: $orientation")
    }

    private fun getArrangement(): LinearLayoutNode.Arrangement {
        return when (val gravity = layoutElement.getAttribute("android:gravity")) {
            "top" -> LinearLayoutNode.Arrangement.Top
            "bottom" -> LinearLayoutNode.Arrangement.Bottom
            "center" -> LinearLayoutNode.Arrangement.Center
            "center_vertical" -> LinearLayoutNode.Arrangement.CenterVertical
            "center_horizontal" -> LinearLayoutNode.Arrangement.CenterHorizontal
            "start", "left" -> LinearLayoutNode.Arrangement.Start
            "end", "right" -> LinearLayoutNode.Arrangement.End
            "" -> LinearLayoutNode.Arrangement.NoArrangement
            else -> throw IllegalStateException("Invalid linear layout gravity type: $gravity")
        }
    }

    private fun children(): Iterable<ViewNode> {
        return object : Iterable<ViewNode> {

            override fun iterator(): Iterator<ViewNode> {
                return ViewGroupIterator(this@LinearLayoutElement)
            }
        }
    }


}