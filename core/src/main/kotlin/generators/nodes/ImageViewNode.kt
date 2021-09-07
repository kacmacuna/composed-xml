package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.DrawableAttribute
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ImageViewNode(
    override val info: Info,
    private val imports: Imports
) : ViewNode {
    override val children: Iterable<ViewNode>
        get() = emptyList()


    override fun body(): CodeBlock {
        val instance = imports.viewImports.image
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("painter = %L", info.src.statement()))

        val modifiers = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = imports.attributeImports.modifier,
            ChainedMemberName(
                prefix = imports.attributeImports.background,
                info.backgroundColor.argument()
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
        paramCodeBlocks.add(CodeBlock.of("contentDescription = \"\""))
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
        return ImageViewNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = layoutWidth ?: info.width,
                height = layoutHeight ?: info.height
            ),
            imports = imports,
        )
    }

    data class Info(
        override val id: ViewId,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName>,
        val backgroundColor: ColorAttribute,
        val src: DrawableAttribute
    ) : ViewInfo

}