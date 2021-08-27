package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

interface LayoutSize {
    fun prefix(): MemberName
    fun argument(): CodeBlock
    fun containsArguments(): Boolean
}

typealias LayoutWidth = LayoutSize
typealias LayoutHeight = LayoutSize