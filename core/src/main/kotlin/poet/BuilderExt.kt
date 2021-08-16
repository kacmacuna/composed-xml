package poet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

fun CodeBlock.Builder.addCodeBlockIf(
    condition: Boolean,
    codeBlock: () -> CodeBlock
): CodeBlock.Builder {
    return if (condition)
        add(codeBlock())
    else
        this
}

fun CodeBlock.Builder.addCodeIf(
    condition: Boolean,
    codeBlock: () -> String
): CodeBlock.Builder {
    return if (condition)
        add(codeBlock())
    else
        this
}

