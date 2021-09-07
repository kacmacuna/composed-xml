package generators.nodes.attributes.constraints

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.joinToCode

class Constraints(
    private val constraintId: String,
    val details: Collection<ConstraintDetails>,
    val isWidthFillToConstraints: Boolean,
    val isHeightFillToConstraints: Boolean,
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
            if (it.margin.dpAttribute.isValid()) {
                attributes.add(it.margin.statement())
            }
            codeBlock.addStatement(
                code,
                attributes.joinToCode()
            )
        }
        if (isWidthFillToConstraints) {
            codeBlock.addStatement(
                "width = %T.fillToConstraints", ServiceLocator.get().imports.attributeImports.dimension
            )
        }
        if (isHeightFillToConstraints) {
            codeBlock.addStatement(
                "height = %T.fillToConstraints", ServiceLocator.get().imports.attributeImports.dimension
            )
        }
        return codeBlock.build()
    }

    companion object {
        const val PARENT = "parent"
    }

}