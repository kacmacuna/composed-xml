package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

class LayoutSizeWrapContent private constructor(
    private val statement: String,
) : LayoutWidth, LayoutHeight {
    override fun statement(): String {
        return "$statement()"
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }

    @Suppress("FunctionName")
    companion object {
        fun Width() = LayoutSizeWrapContent("wrapContentWidth")
        fun Height() = LayoutSizeWrapContent("wrapContentHeight")
    }
}