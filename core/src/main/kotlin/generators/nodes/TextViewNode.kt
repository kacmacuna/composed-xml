package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberCall

class TextViewNode(
    private val info: Info
) : ViewNode {


    override val children: Iterable<ViewNode> = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = ClassName("androidx.compose.material", "Text")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of(""""${info.text}""""))

        val modifiers = ChainedCodeBlock(
            "modifier = Modifier.",
            ChainedMemberCall("background", info.backgroundColor.statement()),
            ChainedMemberCall("weight", if (info.weight >= 0F) "${info.weight}F" else ""),
            ChainedMemberCall(info.width.statement(), "", true),
            ChainedMemberCall(info.height.statement(), "", true)

        ).codeBlock()
        if (modifiers.isNotEmpty())
            paramCodeBlocks.add(modifiers)
        if (info.textColor.isEmpty().not()) {
            paramCodeBlocks.add(CodeBlock.of("color = ${info.textColor.statement()}"))
        }
        if (info.fontSize > 0) {
            paramCodeBlocks.add(CodeBlock.of("fontSize = ${info.fontSize}.sp"))
        }
        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .add("\n")
            .build()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.ui.unit", "sp")
        ) + info.textColor.imports()
    }


    data class Info(
        val id: String,
        val width: LayoutWidth,
        val height: LayoutHeight,
        val text: String,
        val textColor: ColorAttribute,
        val fontSize: Int,
        val backgroundColor: ColorAttribute,
        val weight: Float
    )

}