package generators.nodes.attributes.constraints

import com.squareup.kotlinpoet.CodeBlock
import java.security.PrivateKey
import java.security.PrivilegedActionException

class Constraints(
    private val constraintId: String,
    private val details: Collection<ConstraintDetails>
) {

    val prefix = "constrainAs"

    fun codeBlock(): CodeBlock {
        val detailsToUI = detailsToUI().ifEmpty { return CodeBlock.of("") }
        return CodeBlock.of(
            """
                |${constraintId}Ref, {
                |   $detailsToUI
                |}
            """.trimIndent().trimMargin()
        )
    }

    private fun detailsToUI(): String {
        return details.mapIndexed { pos, it ->
            val code = "${it.constraintDirection.value}.linkTo(${it.constraintToId}.${it.constraintToDirection.value})"
            val newLineOrEmpty = if (pos != details.size - 1) "\n" else ""
            code + newLineOrEmpty
        }.joinToString("") { it }
    }

    companion object {
        const val PARENT = "parent"
    }

}