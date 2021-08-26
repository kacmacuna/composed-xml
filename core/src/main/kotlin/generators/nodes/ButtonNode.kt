package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addCodeBlockIf
import poet.addCodeIf
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberCall

class ButtonNode(
    private val info: Info
) : ViewNode {

    private val textViewNode = TextViewNode(info.textInfo)


    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.textInfo.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = ClassName("androidx.compose.material", "Button")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("onClick = {}"))

        val modifierCodeBlock = ChainedCodeBlock(
            "modifier = Modifier.",
            ChainedMemberCall(info.width.statement(), "", true),
            ChainedMemberCall(info.height.statement(), "", true)
        ).codeBlock()
        if (modifierCodeBlock.isNotEmpty()) paramCodeBlocks.add(modifierCodeBlock)

        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .beginControlFlow(" {")
            .addCodeBlockIf(info.textInfo.text.isNotEmpty()) { textViewNode.body() }
            .endControlFlow()
            .build()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.material", "Button"),
            ClassName("androidx.compose.ui.unit", "sp"),
            ClassName("androidx.compose.material", "Text"),
        ) + info.textInfo.textColor.imports()
    }


    data class Info(
        val textInfo: TextViewNode.Info,
        val width: LayoutWidth,
        val height: LayoutHeight
    )
}