package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

object EmptyLayoutSize : LayoutSize {

    override fun prefix(): MemberName {
        return MemberName("", "")
    }

    override fun argument(): CodeBlock {
        return CodeBlock.of("")
    }

    override fun containsArguments(): Boolean {
        return false
    }
}