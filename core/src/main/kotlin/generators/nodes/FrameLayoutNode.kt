package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.Alignment
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class FrameLayoutNode(
    override val children: Iterable<ViewNode>,
    override val info: Info,
    private val imports: Imports
) : ViewNode {


    override fun body(): CodeBlock {
        val instance = imports.viewImports.box
        val paramCodeBlocks = mutableListOf<CodeBlock>()
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

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth?,
        layoutHeight: LayoutHeight?
    ): ViewNode {
        return FrameLayoutNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = layoutWidth ?: info.width,
                height = layoutHeight ?: info.height
            ),
            imports = imports,
            children = children
        )
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    data class Info(
        override val id: ViewId,
        val alignment: Alignment,
        val backgroundColor: ColorAttribute,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName> = listOf()
    ) : ViewInfo

}