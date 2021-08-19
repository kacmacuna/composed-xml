package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

class LayoutSizeMatchParent private constructor(
    private val statement: String,
) : LayoutWidth, LayoutHeight{

    override fun statement(): String {
        return "$statement()"
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }

    @Suppress("FunctionName")
    companion object {
        fun Width() = LayoutSizeMatchParent("fillMaxWidth")
        fun Height() = LayoutSizeMatchParent("fillMaxHeight")
    }

}