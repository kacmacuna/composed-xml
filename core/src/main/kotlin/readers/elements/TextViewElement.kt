package readers.elements

import generators.nodes.TextViewNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.imports.Imports

class TextViewElement(
    layoutElement: Element,
    private val imports: Imports,
    private val chainedMemberNames: List<ChainedMemberName>
) : LayoutElement<TextViewNode>(layoutElement) {

    override fun node(): TextViewNode {
        return TextViewNode(
            getInfo(),
            imports
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
        weight = getAttribute("android:weight").ifEmpty { "-1" }.toFloat(),
        chainedMemberNames = chainedMemberNames
    )

    private fun getFontSize(): Int {
        return getAttribute("android:textSize").ifEmpty { return 0 }.removeSuffix("sp").toInt()
    }

    private fun getText(): String {
        return getAttribute("android:text")
    }

}