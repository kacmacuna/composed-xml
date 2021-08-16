package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec

interface ViewNode {
    val children: Iterable<ViewNode>
    val parent: ViewNode?

    fun function(): FunSpec
    fun body(): CodeBlock
    fun imports(): Iterable<ClassName>
}