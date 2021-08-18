package readers.elements

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import generators.nodes.attributes.colors.ColorAttributeParser
import org.w3c.dom.Element

class TextViewElement(
    private val layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<TextViewNode>(layoutElement) {

    private val colorAttributeParser = ColorAttributeParser()

    override fun node(): TextViewNode {
        return TextViewNode(
            getInfo(),
            _parent = parentNode
        )
    }

    fun getInfo() = TextViewNode.Info(
        id = getViewIdNameTag(),
        text = getText(layoutElement),
        textColor = colorAttributeParser.parse(layoutElement.getAttribute("android:textColor")),
        fontSize = getFontSize(layoutElement),
    )

    private fun getFontSize(documentElement: Element): Int {
        return documentElement.getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(documentElement: Element): String {
        return documentElement.getAttribute("android:text")
    }

}