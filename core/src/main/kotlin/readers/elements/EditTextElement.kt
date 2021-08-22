package readers.elements

import generators.nodes.EditTextNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class EditTextElement(
    layoutElement: Element
) : LayoutElement<EditTextNode>(layoutElement) {

    override fun node(): EditTextNode {
        return EditTextNode(
            EditTextNode.Info(
                id = getViewIdNameTag(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                weight = getAttribute("android:weight").ifEmpty { "-1" }.toFloat(),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
            )
        )
    }

}