package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.Alignment
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberCall

class FrameLayoutNode(
    override val children: Iterable<ViewNode>,
    private val info: Info,
) : ViewNode {


    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = ClassName("androidx.compose.foundation.layout", "Box")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        val modifiers = ChainedCodeBlock(
            "modifier = Modifier.",
            ChainedMemberCall("background", info.backgroundColor.statement()),
            ChainedMemberCall(info.width.statement(), "", true),
            ChainedMemberCall(info.height.statement(), "", true)

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