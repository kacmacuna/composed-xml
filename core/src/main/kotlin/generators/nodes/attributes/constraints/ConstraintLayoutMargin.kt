package generators.nodes.attributes.constraints

import com.squareup.kotlinpoet.CodeBlock
import generators.nodes.attributes.size.DPAttribute
import generators.nodes.attributes.xmlValueAsVariable

class ConstraintLayoutMargin(
    val dpAttribute: DPAttribute
) {

    fun localVariable(): CodeBlock {
        return if (dpAttribute.isResource())
            CodeBlock.of("val ${localVariableName()} = %L", dpAttribute.statement())
        else
            CodeBlock.of("")
    }

    fun statement(): CodeBlock {
        return if (dpAttribute.isResource())
            CodeBlock.of(localVariableName())
        else
            dpAttribute.statement()
    }

    private fun localVariableName(): String {
        return dpAttribute.resourceValue().xmlValueAsVariable()
    }

}