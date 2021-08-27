package generators.nodes.attributes.constraints

import generators.nodes.ViewNode
import org.w3c.dom.Element
import readers.elements.LayoutElement
import java.util.*

class ConstraintsParser {

    fun parse(element: LayoutElement<ViewNode>): Constraints {
        return Constraints(
            constraintId = element.getViewIdNameTag().replaceFirstChar { it.lowercase() },
            details = parseAsConstraints(element)
        )
    }

    private fun parseAsConstraints(element: LayoutElement<ViewNode>): List<ConstraintDetails> {
        return ConstraintType.values().filter {
            element.hasAttribute(it.attributeTag)
        }.map {
            val attributeValue = element.getAttribute(it.attributeTag)
            ConstraintDetails(
                constraintDirection = it.thisDirection,
                constraintToId = if (attributeValue == "parent")
                    Constraints.PARENT
                else
                    "${attributeValue.removePrefix("@id/")}Ref",
                constraintToDirection = it.toDirection
            )
        }
    }

    private enum class ConstraintType(
        val attributeTag: String,
        val thisDirection: ConstraintDirection,
        val toDirection: ConstraintDirection
    ) {
        TopTopOf("app:layout_constraintTop_toTopOf", ConstraintDirection.Top, ConstraintDirection.Top),
        TopStartOf("app:layout_constraintTop_toStartOf", ConstraintDirection.Top, ConstraintDirection.Start),
        TopBottomOf("app:layout_constraintTop_toBottomOf", ConstraintDirection.Top, ConstraintDirection.Bottom),
        TopEndOf("app:layout_constraintTop_toEndOf", ConstraintDirection.Top, ConstraintDirection.End),

        StartTopOf("app:layout_constraintStart_toTopOf", ConstraintDirection.Start, ConstraintDirection.Top),
        StartStartOf("app:layout_constraintStart_toStartOf", ConstraintDirection.Start, ConstraintDirection.Start),
        StartBottomOf("app:layout_constraintStart_toBottomOf", ConstraintDirection.Start, ConstraintDirection.Bottom),
        StartEndOf("app:layout_constraintStart_toEndOf", ConstraintDirection.Start, ConstraintDirection.End),

        BottomTopOf("app:layout_constraintBottom_toTopOf", ConstraintDirection.Bottom, ConstraintDirection.Top),
        BottomStartOf("app:layout_constraintBottom_toStartOf", ConstraintDirection.Bottom, ConstraintDirection.Start),
        BottomBottomOf(
            "app:layout_constraintBottom_toBottomOf",
            ConstraintDirection.Bottom,
            ConstraintDirection.Bottom
        ),
        BottomEndOf("app:layout_constraintBottom_toEndOf", ConstraintDirection.Bottom, ConstraintDirection.End),

        EndTopOf("app:layout_constraintEnd_toTopOf", ConstraintDirection.End, ConstraintDirection.Top),
        EndStartOf("app:layout_constraintEnd_toStartOf", ConstraintDirection.End, ConstraintDirection.Start),
        EndBottomOf("app:layout_constraintEnd_toBottomOf", ConstraintDirection.End, ConstraintDirection.Bottom),
        EndEndOf("app:layout_constraintEnd_toEndOf", ConstraintDirection.End, ConstraintDirection.End);
    }

}