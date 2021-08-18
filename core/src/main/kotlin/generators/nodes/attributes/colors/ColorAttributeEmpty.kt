package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName

object ColorAttributeEmpty : ColorAttribute {
    override fun statement(): String {
        return ""
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }

    override fun isEmpty(): Boolean {
        return true
    }
}