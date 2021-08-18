package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName

interface ColorAttribute {

    fun statement(): String
    fun imports(): Iterable<ClassName>

    fun isEmpty(): Boolean

}