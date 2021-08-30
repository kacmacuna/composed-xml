package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class LayoutSizeMatchParent private constructor(
    private val statement: String,
) : LayoutSize {

    override fun prefix(): MemberName {
        return if (statement == "fillMaxWidth")
            ServiceLocator.get().imports.attributeImports.fillMaxWidth
        else
            ServiceLocator.get().imports.attributeImports.fillMaxHeight
    }

    override fun argument(): CodeBlock {
        return CodeBlock.of("")
    }

    override fun containsArguments(): Boolean {
        return false
    }

    @Suppress("FunctionName")
    companion object {
        fun Width() = LayoutSizeMatchParent("fillMaxWidth")
        fun Height() = LayoutSizeMatchParent("fillMaxHeight")
    }

}