package readers.elements

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import generators.nodes.elements.colors.ColorElementParser
import org.w3c.dom.Element

class TextViewElement(
    private val layoutElement: Element,
    private val parentNode: ViewNode?
) : LayoutElement<TextViewNode>(layoutElement) {

    private val colorElementParser = ColorElementParser()

    override fun node(): TextViewNode {
        return TextViewNode(
            getInfo(),
            parent = parentNode
        )
    }

    fun getInfo() = TextViewNode.Info(
        id = getViewIdNameTag(),
        text = getText(layoutElement),
        textColor = colorElementParser.parse(layoutElement.getAttribute("android:textColor")),
        fontSize = getFontSize(layoutElement),
    )

    private fun getFontSize(documentElement: Element): Int {
        return documentElement.getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(documentElement: Element): String {
        return documentElement.getAttribute("android:text")
    }

}