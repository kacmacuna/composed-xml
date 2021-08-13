package readers

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import generators.nodes.elements.colors.ColorElementParser
import org.w3c.dom.Element
import java.util.*

class TextViewReader(
    private val layoutElement: LayoutElement,
    private val parentNode: ViewNode?
) {

    private val colorElementParser = ColorElementParser()

    fun node(): TextViewNode {
        return TextViewNode(
            TextViewNode.Info(
                id = layoutElement.getViewIdNameTag(),
                text = getText(layoutElement),
                textColor = colorElementParser.parse(layoutElement.getAttribute("android:textColor")),
                fontSize = getFontSize(layoutElement),
            ),
            parent = parentNode
        )
    }

    private fun getFontSize(documentElement: Element): Int {
        return documentElement.getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(documentElement: Element): String {
        return documentElement.getAttribute("android:text")
    }

}