package generators.nodes.elements.colors

import com.squareup.kotlinpoet.ClassName

interface ColorElement {

    fun statement(): String
    fun imports(): Iterable<ClassName>

    fun isEmpty(): Boolean

}