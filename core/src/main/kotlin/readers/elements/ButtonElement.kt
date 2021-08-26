package readers.elements

import generators.nodes.ButtonNode
import generators.nodes.ViewNode
import generators.nodes.attributes.constraints.ConstraintDetails
import generators.nodes.attributes.constraints.ConstraintDirection
import generators.nodes.attributes.constraints.Constraints
import org.w3c.dom.Element

class ButtonElement(
    layoutElement: Element
) : LayoutElement<ButtonNode>(layoutElement) {

    private val textViewElement = TextViewElement(layoutElement)

    override fun node(): ButtonNode {
        return ButtonNode(
            info = getInfo()
        )
    }

    private fun getInfo(): ButtonNode.Info {
        val textViewInfo = textViewElement.getInfo()
        return ButtonNode.Info(
            textInfo = textViewInfo,
            width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
            height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
            constraints = Constraints(
                constraintId = textViewInfo.id.replaceFirstChar {
                    it.lowercase()
                },
                details = constraintDetails()
            )
        )
    }

    private fun constraintDetails(): List<ConstraintDetails> {
        val list = mutableListOf<ConstraintDetails>()
        val topTopOf = getAttribute("app:layout_constraintTop_toTopOf")
        if (topTopOf.isNotEmpty()) {
            if (topTopOf == "parent")
                list.add(
                    ConstraintDetails(
                        ConstraintDirection.Top,
                        constraintToId = Constraints.PARENT,
                        constraintToDirection = ConstraintDirection.Top
                    )
                )
        }

        return list
    }
}