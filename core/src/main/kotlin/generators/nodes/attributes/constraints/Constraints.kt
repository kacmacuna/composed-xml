package generators.nodes.attributes.constraints

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.joinToCode
import readers.imports.Imports

class Constraints(
    private val constraintId: String,
    private val details: Collection<ConstraintDetails>
) {

    val memberNamePrefix = ServiceLocator.get().imports.viewImports.constraintLayout.constrainAs

    fun codeBlock(): CodeBlock {
        val detailsToUI = detailsToUI().also {
            if (it.isEmpty()) return CodeBlock.of("")
        }
        return CodeBlock.builder()
            .beginControlFlow("${constraintId}Ref, {")
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
            if (it.margin > 0) {
                attributes.add(
                    CodeBlock.of("${it.margin}.%M", ServiceLocator.get().imports.attributeImports.dp)
                )
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