package readers

import generators.nodes.TextViewNode
import generators.nodes.elements.colors.ColorElementParser
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.*

class TextViewReader(private val document: LayoutDocument) {

    private val colorElementParser = ColorElementParser()

    fun node(): TextViewNode {
        return TextViewNode(
            TextViewNode.Info(
                id = document.getViewIdNameTag(document.documentElement),
                text = getText(document.documentElement),
                textColor = colorElementParser.parse(document.documentElement.getAttribute("android:textColor")),
                fontSize = getFontSize(document.documentElement)
            )
        )
    }

    private fun getFontSize(documentElement: Element): Int {
        return documentElement.getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(documentElement: Element): String {
        return documentElement.getAttribute("android:text")
    }

}