package poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.addCodeBlockIf(
    condition: Boolean,
    codeBlock: () -> CodeBlock
): FunSpec.Builder {
    return if (condition)
        addCode(codeBlock())
    else
        this
}

fun FunSpec.Builder.addCodeIf(
    condition: Boolean,
    codeBlock: () -> String
): FunSpec.Builder {
    return if (condition)
        addCode(codeBlock())
    else
        this
}

