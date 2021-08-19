package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

object EmptyLayoutSize : LayoutWidth, LayoutHeight {
    override fun statement(): String {
        return ""
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }
}