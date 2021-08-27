package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.Alignment
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName

class FrameLayoutNode(
    override val children: Iterable<ViewNode>,
    private val info: Info,
) : ViewNode {
    override val id: String
        get() = info.id


    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = GenerationEngine.get().className("androidx.compose.foundation.layout", "Box")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = GenerationEngine.get().className("androidx.compose.ui", "Modifier"),
            ChainedMemberName(
                prefix = GenerationEngine.get().memberName("androidx.compose.foundation", "background"),
                info.backgroundColor.argument()
            ),
            ChainedMemberName(
                prefix = info.width.prefix(),
                info.width.argument(),
                containsArguments = true
            ),
            ChainedMemberName(
                prefix = info.height.prefix(),
                info.height.argument(),
                containsArguments = true
            )
        ).codeBlock()
        if (modifiers.isNotEmpty()) paramCodeBlocks.add(modifiers)

        if (info.alignment != Alignment.NoAlignment) {
            paramCodeBlocks.add(CodeBlock.of("contentAlignment = Box.Alignment.${info.alignment.name}"))
        }

        return CodeBlock.builder()
            .add("%T (%L)", instance, paramCodeBlocks.joinToCode())
            .add(
                CodeBlock.builder()
                    .beginControlFlow(" {")
                    .add(childrenToCodeBlock())
                    .endControlFlow()
                    .build()
            )
            .build()
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(ClassName("androidx.compose.foundation.layout", "Box")) + children.map { it.imports() }.flatten()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    class Info(
        val id: String,
        val alignment: Alignment,
        val backgroundColor: ColorAttribute,
        val width: LayoutWidth,
        val height: LayoutHeight
    ) {
        fun hasAnyAttribute(): Boolean {
            return alignment != Alignment.NoAlignment || backgroundColor.isEmpty().not()
        }

        fun hasAnyModifierAndAlignment(): Boolean {
            return backgroundColor.isEmpty().not() && alignment != Alignment.NoAlignment
        }
    }

}