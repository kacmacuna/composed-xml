package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

class LayoutSizeInDp private constructor(
    private val size: Int,
    private val prefix: String
) : LayoutSize {

    override fun prefix(): MemberName {
        return GenerationEngine.get().memberName("androidx.compose.foundation.layout", prefix)
    }

    override fun argument(): CodeBlock {
        return CodeBlock.builder()
            .add(
                "$size.%M",
                GenerationEngine.get().memberName("androidx.compose.ui.unit", "dp"),
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