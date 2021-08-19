package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.ViewNode
import generators.nodes.attributes.layout.LayoutSizeAttributeParser
import org.w3c.dom.Element

class ButtonElement(
    layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<ButtonNode>(layoutElement) {

    private val layoutSizeAttributeParser = LayoutSizeAttributeParser()

    private val textViewElement = TextViewElement(layoutElement, parentNode)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo(),
            _parent = parentNode
        )
    }

    private fun getInfo(): ButtonNode.Info {
        val textViewInfo = textViewElement.getInfo()
        return ButtonNode.Info(
            textInfo = textViewInfo,
            width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
            height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
        )
    }
}