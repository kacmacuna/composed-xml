package readers.elements

import generators.nodes.FrameLayoutNode
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.elements.viewgroup.ViewGroupIterator
import readers.imports.Imports

class FrameLayoutElement(
    element: Element,
    private val imports: Imports,
    private val chainedMemberNames: List<ChainedMemberName>
) : LayoutElement<FrameLayoutNode>(element) {


    override fun node(): FrameLayoutNode {
        return FrameLayoutNode(
            children = Iterable { ViewGroupIterator(this, imports) },
            info = FrameLayoutNode.Info(
                id = getViewIdNameTag(),
                alignment = getAlignmentFromGravity(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
                chainedMemberNames = chainedMemberNames
            ),
            imports
        )
    }

}