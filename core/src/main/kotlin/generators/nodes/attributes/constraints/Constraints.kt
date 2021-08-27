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
            val code = "${it.constraintDirection.value}.linkTo(${it.constraintToId}.${it.constraintToDirection.value})"
            codeBlock.addStatement(code)
        }
        return codeBlock.build()
    }

    companion object {
        const val PARENT = "parent"
    }

}