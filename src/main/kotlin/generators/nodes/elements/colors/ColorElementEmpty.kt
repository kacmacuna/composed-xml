package generators.nodes.elements.colors

import com.squareup.kotlinpoet.ClassName

object ColorElementEmpty : ColorElement {
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