package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

interface LayoutHeight {
    fun statement(): String
    fun imports(): Iterable<ClassName>
}