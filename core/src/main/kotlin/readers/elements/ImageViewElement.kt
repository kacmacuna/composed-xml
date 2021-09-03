package readers.elements

import generators.nodes.ImageViewNode
import generators.nodes.attributes.DrawableAttribute
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ImageViewElement(
    element: Element,
    private val imports: Imports,
    private val chainedMemberNames: List<ChainedMemberName>
) : LayoutElement<ImageViewNode>(element) {
    override fun node(): ImageViewNode {
        return ImageViewNode(
            ImageViewNode.Info(
                id = getViewIdNameTag(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
                chainedMemberNames = chainedMemberNames,
                src = DrawableAttribute(getAttribute("android:src"))
            ),
            imports
        )
    }
}