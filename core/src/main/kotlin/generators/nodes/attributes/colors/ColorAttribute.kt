package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

interface ColorAttribute {

    fun argument(): CodeBlock
    fun imports(): Iterable<ClassName>

    fun isEmpty(): Boolean

}