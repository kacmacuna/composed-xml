package readers.elements

import generators.nodes.ConstraintLayoutNode
import generators.nodes.FrameLayoutNode
import org.w3c.dom.Element

class ConstraintLayoutElement(element: Element) : LayoutElement<ConstraintLayoutNode>(element) {
    override fun node(): ConstraintLayoutNode {
        return ConstraintLayoutNode(
            children = Iterable { ViewGroupIterator(this) },
            info = ConstraintLayoutNode.Info(
                id = getViewIdNameTag(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height"))
            )
        )
    }

}
