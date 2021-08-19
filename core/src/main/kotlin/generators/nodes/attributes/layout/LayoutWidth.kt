package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName

interface LayoutWidth  {
    fun statement(): String
    fun imports(): Iterable<ClassName>
}