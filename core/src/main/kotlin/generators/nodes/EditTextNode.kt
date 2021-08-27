package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName

class EditTextNode(
    private val info: Info
) : ViewNode {

    override val children: Iterable<ViewNode>
        get() = emptyList()
    override val id: String
        get() = info.id

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = GenerationEngine.get().className("androidx.compose.material", "TextField")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("value = \"\""))

        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = GenerationEngine.get().className("androidx.compose.ui", "Modifier"),
            ChainedMemberName(
                prefix = GenerationEngine.get().memberName("androidx.compose.foundation", "background"),
                info.backgroundColor.argument()
            ),
            ChainedMemberName(
                prefix = MemberName("", "weight"),
                CodeBlock.of(if (info.weight >= 0F) "${info.weight}F" else "")
            ),
            ChainedMemberName(
                prefix = info.width.prefix(),
                info.width.argument(),
                containsArguments = info.width.containsArguments()
            ),
            ChainedMemberName(
                prefix = info.height.prefix(),
                info.height.argument(),
                containsArguments = info.height.containsArguments()
            ),
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