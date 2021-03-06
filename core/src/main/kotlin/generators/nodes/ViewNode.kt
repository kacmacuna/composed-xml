package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedMemberName

interface ViewNode {
    val children: Iterable<ViewNode>
    val info: ViewInfo

    fun body(): CodeBlock
    fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth? = null,
        layoutHeight: LayoutHeight? = null
    ): ViewNode
}