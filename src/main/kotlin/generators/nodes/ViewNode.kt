package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

interface ViewNode {
    val children: Iterable<ViewNode>
    fun generate(): FunSpec
    fun imports(): Iterable<ClassName>
}