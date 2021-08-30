package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.attributes.constraints.ConstraintDetails
import generators.nodes.attributes.constraints.ConstraintDirection
import generators.nodes.attributes.constraints.Constraints
import org.w3c.dom.Element
import readers.imports.Imports

class ButtonElement(
    layoutElement: Element,
    private val imports: Imports
) : LayoutElement<ButtonNode>(layoutElement) {

    private val textViewElement = TextViewElement(layoutElement, imports)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo(), imports
        )
    }

    private fun getInfo(): ButtonNode.Info {
        val textViewInfo = textViewElement.getInfo().copy(constraints = Constraints.EMPTY)
        return ButtonNode.Info(
            textInfo = textViewInfo,
            width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
            height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
            constraints = constraintsParser.parse(this)
        )
    }

}