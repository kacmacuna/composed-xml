package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

class LayoutSizeInDp private constructor(
    private val size: Int,
    private val prefix: String
) : LayoutHeight, LayoutWidth {

    override fun statement(): String {
        return "$prefix($size.dp)"
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(ClassName("androidx.compose.ui.unit", "sp"))
    }

    @Suppress("FunctionName")
    companion object {
        fun Width(size: Int) = LayoutSizeInDp(size, "width")
        fun Height(size: Int) = LayoutSizeInDp(size, "height")
    }
}