package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class ButtonElement(
    layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<ButtonNode>(layoutElement) {

    private val textViewElement = TextViewElement(layoutElement, parentNode)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo(),
            parent = parentNode
        )
    }

    private fun getInfo(): ButtonNode.Info {
        val textViewInfo = textViewElement.getInfo()
        return ButtonNode.Info(
            id = textViewInfo.id,
            text = textViewInfo.text,
            textColor = textViewInfo.textColor,
            fontSize = textViewInfo.fontSize
        )
    }
}