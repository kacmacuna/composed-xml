package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.TextAttribute
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class EditTextNode(
    override val info: Info,
    private val imports: Imports
) : ViewNode {

    override val children: Iterable<ViewNode>
        get() = emptyList()


    override fun body(): CodeBlock {
        val instance = imports.viewImports.textField
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("value = %L", info.text.statement()))

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
        paramCodeBlocks.add(CodeBlock.of("onValueChange = {}"))
        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .add("\n")
            .build()
    }

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth?,
        layoutHeight: LayoutHeight?
    ): ViewNode {
        return EditTextNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = layoutWidth ?: info.width,
                height = layoutHeight ?: info.height
            ),
            imports = imports
        )
    }

    data class Info(
        override val id: ViewId,
        val backgroundColor: ColorAttribute,
        val weight: Float,
        val text: TextAttribute,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName> = listOf(),
    ) : ViewInfo

}