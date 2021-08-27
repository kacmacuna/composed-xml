package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

object ColorAttributeEmpty : ColorAttribute {
    override fun statement(): String {
        return ""
    }

    override fun argument(): CodeBlock {
        return CodeBlock.of("")
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }

    override fun isEmpty(): Boolean {
        return true
    }
}