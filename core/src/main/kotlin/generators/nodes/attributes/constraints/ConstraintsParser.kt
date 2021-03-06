package generators.nodes.attributes.constraints

import generators.nodes.ViewNode
import generators.nodes.attributes.ViewIdAsVariable
import generators.nodes.attributes.size.DPAttribute
import org.w3c.dom.Element
import readers.elements.LayoutElement
import java.util.*

class ConstraintsParser {

    fun parse(element: Element): Constraints {
        return Constraints(
            constraintId = getViewIdNameTag(element),
            details = parseAsConstraints(element),
            isWidthFillToConstraints = element.getAttribute("android:layout_width") == "0dp",
            isHeightFillToConstraints = element.getAttribute("android:layout_height") == "0dp",
        )
    }

    private fun getViewIdNameTag(element: Element): String {
        val idName = element.getAttribute("android:id").removePrefix("@+id/")
        return if (idName.isNotEmpty()) {
            ViewIdAsVariable("${idName}Ref").toString()
        } else {
            "ConstrainedLayoutReference(Any())"
        }
    }

    private fun parseAsConstraints(element: Element): List<ConstraintDetails> {
        return ConstraintType.values().filter {
            element.hasAttribute(it.attributeTag)
        }.map {
            val attributeValue = element.getAttribute(it.attributeTag)
            ConstraintDetails(
                constraintDirection = it.thisDirection,
                constraintToId = if (attributeValue == "parent")
                    Constraints.PARENT
                else
                    "${ViewIdAsVariable(attributeValue.removePrefix("@id/"))}Ref",
                constraintToDirection = it.toDirection,
                margin = ConstraintLayoutMargin(DPAttribute(getMarginFromDirection(it, element)))
            )
        }
    }

    private fun getMarginFromDirection(
        it: ConstraintType,
        element: Element
    ): String {
        val attribute = when (it.thisDirection) {
            ConstraintDirection.Top -> element.getAttribute("android:layout_marginTop")
            ConstraintDirection.Bottom -> element.getAttribute("android:layout_marginBottom")
            ConstraintDirection.Start -> element.getAttribute("android:layout_marginStart")
            ConstraintDirection.End -> element.getAttribute("android:layout_marginEnd")
        }.ifEmpty { null } ?: when (it.thisDirection) {
            ConstraintDirection.Top,
            ConstraintDirection.Bottom -> element.getAttribute("android:layout_marginVertical")
            ConstraintDirection.Start,
            ConstraintDirection.End -> element.getAttribute("android:layout_marginHorizontal")
        }.ifEmpty { null } ?: element.getAttribute("android:layout_margin")
        return attribute
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