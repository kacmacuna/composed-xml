package generators.nodes.attributes.constraints

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.joinToCode

class Constraints(
    private val constraintId: String,
    private val details: Collection<ConstraintDetails>
) {

    val memberNamePrefix = ServiceLocator.get().imports.viewImports.constraintLayout.constrainAs

    fun argument(): CodeBlock {
        val detailsToUI = detailsToUI().also {
            if (it.isEmpty()) return CodeBlock.of("")
        }
        return CodeBlock.builder()
            .beginControlFlow("${constraintId}, {")
            .add(detailsToUI)
            .endControlFlow()
            .build()
    }

    private fun detailsToUI(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        details.forEach {
            val code = "${it.constraintDirection.value}.linkTo(%L)"
            val attributes = mutableListOf(
                CodeBlock.of("${it.constraintToId}.${it.constraintToDirection.value}"),
            )
            if (it.margin.isValid()) {
                attributes.add(it.margin.statement())
            }
            codeBlock.addStatement(
                code,
                attributes.joinToCode()
            )
        }
        return codeBlock.build()
    }

    companion object {
        const val PARENT = "parent"
        val EMPTY = Constraints("", emptyList())
    }

}