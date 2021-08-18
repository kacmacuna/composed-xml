package readers.elements

import generators.nodes.FrameLayoutNode
import generators.nodes.ViewNode
import generators.nodes.attributes.colors.ColorAttributeParser
import org.w3c.dom.Element

class FrameLayoutElement(
    element: Element,
    private val parentNode: ViewNode?
) : LayoutElement<FrameLayoutNode>(element) {

    private val colorAttributeParser = ColorAttributeParser()

    override fun node(): FrameLayoutNode {
        return FrameLayoutNode(
            children = Iterable { ViewGroupIterator(this) },
            info = FrameLayoutNode.Info(
                id = getViewIdNameTag(),
                alignment = getAlignmentFromGravity(),
                backgroundColorAttribute = colorAttributeParser.parse(getAttribute("android:background"))
            ),
            _parent = parentNode
        )
    }

}