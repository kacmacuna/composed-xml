package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class ButtonElement(
    layoutElement: Element
) : LayoutElement<ButtonNode>(layoutElement) {

    private val textViewElement = TextViewElement(layoutElement)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo()
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