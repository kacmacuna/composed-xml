package readers.elements

import generators.nodes.FrameLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class FrameLayoutElement(
    element: Element
) : LayoutElement<FrameLayoutNode>(element) {


    override fun node(): FrameLayoutNode {
        return FrameLayoutNode(
            children = Iterable { ViewGroupIterator(this) },
            info = FrameLayoutNode.Info(
                id = getViewIdNameTag(),
                alignment = getAlignmentFromGravity(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height"))
            )
        )
    }

}