package generators.nodes.attributes.layout

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

object EmptyLayoutSize : LayoutSize {

    override fun prefix(): MemberName {
        return GenerationEngine.get().memberName("", "")
    }

    override fun argument(): CodeBlock {
        return CodeBlock.of("")
    }

    override fun containsArguments(): Boolean {
        return false
    }
}