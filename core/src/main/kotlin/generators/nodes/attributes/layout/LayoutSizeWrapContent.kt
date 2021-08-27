package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class LayoutSizeWrapContent private constructor(
    private val statement: String,
) : LayoutSize {

    override fun prefix(): MemberName {
        return GenerationEngine.get().memberName("androidx.compose.foundation.layout", statement)
    }

    override fun argument(): CodeBlock {
        return CodeBlock.of("")
    }

    override fun containsArguments(): Boolean {
        return false
    }

    @Suppress("FunctionName")
    companion object {
        fun Width() = LayoutSizeWrapContent("wrapContentWidth")
        fun Height() = LayoutSizeWrapContent("wrapContentHeight")
    }
}