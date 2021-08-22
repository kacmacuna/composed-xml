package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.addComposeAnnotation
import poet.chained.ChainedMemberCall

class EditTextNode(
    private val info: Info
) : ViewNode {

    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = ClassName("", "TextField")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("value = \"\""))

        val modifiers = ChainedCodeBlock(
            "modifier = Modifier.",
            ChainedMemberCall("background", info.backgroundColor.statement()),
            ChainedMemberCall("weight", if (info.weight >= 0F) "${info.weight}F" else ""),
            ChainedMemberCall(info.width.statement(), "", true),
            ChainedMemberCall(info.height.statement(), "", true)

        ).codeBlock()
        if (modifiers.isNotEmpty())
            paramCodeBlocks.add(modifiers)
        paramCodeBlocks.add(CodeBlock.of("onValueChange = {}"))
        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .build()
    }

    override fun imports(): Iterable<ClassName> {
        return emptyList()
    }

    data class Info(
        val id: String,
        val backgroundColor: ColorAttribute,
        val weight: Float,
        val width: LayoutWidth,
        val height: LayoutHeight
    )

}