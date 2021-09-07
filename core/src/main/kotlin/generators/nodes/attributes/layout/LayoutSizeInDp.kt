package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class LayoutSizeInDp private constructor(
    val size: Int,
    private val prefix: String
) : LayoutSize {

    override fun prefix(): MemberName {
        val attributeImports = ServiceLocator.get().imports.attributeImports
        return if (prefix == "width")
            attributeImports.layoutWidth
        else
            attributeImports.layoutHeight
    }

    override fun argument(): CodeBlock {
        return CodeBlock.builder()
            .add(
                "$size.%M",
                    ServiceLocator.get().imports.attributeImports.dp
            ).build()
    }

    override fun containsArguments(): Boolean {
        return true
    }


    @Suppress("FunctionName")
    companion object {
        fun Width(size: Int) = LayoutSizeInDp(size, "width")
        fun Height(size: Int) = LayoutSizeInDp(size, "height")
    }
}