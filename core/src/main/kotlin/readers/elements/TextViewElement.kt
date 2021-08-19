package readers.elements

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import generators.nodes.attributes.colors.ColorAttributeParser
import generators.nodes.attributes.layout.LayoutSizeAttributeParser
import org.w3c.dom.Element

class TextViewElement(
    private val layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<TextViewNode>(layoutElement) {

    private val colorAttributeParser = ColorAttributeParser()
    private val layoutSizeAttributeParser = LayoutSizeAttributeParser()

    override fun node(): TextViewNode {
        return TextViewNode(
            getInfo(),
            _parent = parentNode
        )
    }

    fun getInfo() = TextViewNode.Info(
        id = getViewIdNameTag(),
        text = getText(),
        textColor = colorAttributeParser.parse(getAttribute("android:textColor")),
        fontSize = getFontSize(),
        width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
        height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height"))
    )

    private fun getFontSize(): Int {
        return getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(): String {
        return getAttribute("android:text")
    }

}