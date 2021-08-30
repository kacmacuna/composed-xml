package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class LayoutSizeWrapContent private constructor(
    private val statement: String,
) : LayoutSize {

    override fun prefix(): MemberName {
        return if (statement == "wrapContentWidth")
            ServiceLocator.get().imports.attributeImports.wrapContentWidth
        else
            ServiceLocator.get().imports.attributeImports.wrapContentHeight
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