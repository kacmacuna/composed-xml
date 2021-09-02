package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.TextAttribute
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class TextViewNode(
    private val info: Info,
    private val imports: Imports
) : ViewNode {


    override val children: Iterable<ViewNode> = emptyList()
    override val id: String
        get() = info.id.getIdOrDefault()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id.getIdOrDefault())
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val instance = imports.viewImports.text
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(info.text.statement())

        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = imports.attributeImports.modifier,
            ChainedMemberName(
                prefix = imports.attributeImports.background,
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
            *info.chainedMemberNames.toTypedArray()
        ).codeBlock()
        if (modifiers.isNotEmpty())
            paramCodeBlocks.add(modifiers)
        if (info.textColor.isEmpty().not()) {
            paramCodeBlocks.add(CodeBlock.of("color = %L", info.textColor.argument()))
        }
        if (info.fontSize > 0) {
            paramCodeBlocks.add(CodeBlock.of("fontSize = ${info.fontSize}.sp"))
        }
        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .add("\n")
            .build()
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.ui.unit", "sp")
        ) + info.textColor.imports()
    }

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth,
        layoutHeight: LayoutHeight
    ): ViewNode {
        return TextViewNode(
            info = info.copy(chainedMemberNames = info.chainedMemberNames + chainedMemberNames),
            imports = imports
        )
    }


    data class Info(
        override val id: ViewId,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName> = listOf(),
        val text: TextAttribute,
        val textColor: ColorAttribute,
        val fontSize: Int,
        val backgroundColor: ColorAttribute,
        val weight: Float,
    ) : ViewInfo

}