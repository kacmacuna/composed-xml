package readers.elements

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class TextViewElement(
    layoutElement: Element
) : LayoutElement<TextViewNode>(layoutElement) {

    override fun node(): TextViewNode {
        return TextViewNode(
            getInfo()
        )
    }

    fun getInfo() = TextViewNode.Info(
        id = getViewIdNameTag(),
        text = getText(),
        textColor = colorAttributeParser.parse(getAttribute("android:textColor")),
        fontSize = getFontSize(),
        width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
        height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
        backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
        weight = getAttribute("android:weight").ifEmpty { "-1" }.toFloat()
    )

    private fun getFontSize(): Int {
        return getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(): String {
        return getAttribute("android:text")
    }

}