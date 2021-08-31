package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.attributes.constraints.Constraints
import generators.nodes.attributes.layout.EmptyLayoutSize
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ButtonElement(
    layoutElement: Element,
    private val imports: Imports,
    private val chainedMemberNames: List<ChainedMemberName>
) : LayoutElement<ButtonNode>(layoutElement) {

    private val textViewElement = TextViewElement(layoutElement, imports, chainedMemberNames)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo(), imports
        )
    }

    private fun getInfo(): ButtonNode.Info {
        val textViewInfo = textViewElement.getInfo().copy(
            width = EmptyLayoutSize,
            height = EmptyLayoutSize,
            chainedMemberNames = emptyList()
        )
        return ButtonNode.Info(
            textInfo = textViewInfo,
            width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
            height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
            chainedMemberNames = chainedMemberNames
        )
    }

}