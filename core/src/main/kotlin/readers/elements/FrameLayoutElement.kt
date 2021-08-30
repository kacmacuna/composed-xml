package readers.elements

import generators.nodes.FrameLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import readers.imports.Imports

class FrameLayoutElement(
    element: Element,
    private val imports: Imports
) : LayoutElement<FrameLayoutNode>(element) {


    override fun node(): FrameLayoutNode {
        return FrameLayoutNode(
            children = Iterable { ViewGroupIterator(this, imports) },
            info = FrameLayoutNode.Info(
                id = getViewIdNameTag(),
                alignment = getAlignmentFromGravity(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height"))
            ),
            imports
        )
    }

}