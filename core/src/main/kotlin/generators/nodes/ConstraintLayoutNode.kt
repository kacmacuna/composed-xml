package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.joinToCode
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName

class ConstraintLayoutNode(
    override val children: Iterable<ViewNode>,
    private val info: Info
) : ViewNode {
    override val id: String
        get() = info.id

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = GenerationEngine.get().className("androidx.compose.foundation.layout", "ConstraintLayout")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = GenerationEngine.get().memberName("androidx.compose.ui", "Modifier"),
            ChainedMemberName("background", info.backgroundColor.statement()),
            ChainedMemberName(info.width.statement(), "", true),
            ChainedMemberName(info.height.statement(), "", true)

        ).codeBlock()
        if (modifiers.isNotEmpty()) paramCodeBlocks.add(modifiers)

        return CodeBlock.builder()
            .add("%T (%L)", instance, paramCodeBlocks.joinToCode())
            .add(
                CodeBlock.builder()
                    .beginControlFlow(" {")
                    .add(createConstraintRefs())
                    .add(childrenToCodeBlock())
                    .endControlFlow()
                    .build()
            )
            .build()
    }

    private fun createConstraintRefs(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.addStatement(
                "val ${it.id.replaceFirstChar { first -> first.lowercase() }}Ref = %M(Any())",
                GenerationEngine.get().memberName("androidx.compose.foundation.layout", "ConstrainedLayoutReference")
            )
        }
        return codeBlock.build()
    }

    override fun imports(): Iterable<ClassName> {
        return children.map { it.imports() }.flatten()
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    data class Info(
        val id: String,
        val backgroundColor: ColorAttribute,
        val width: LayoutWidth,
        val height: LayoutHeight
    )

}
