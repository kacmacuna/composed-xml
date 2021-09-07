package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.joinToCode
import generators.nodes.attributes.ViewIdAsVariable
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.constraints.ConstraintLayoutMargin
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutSizeInDp
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ConstraintLayoutNode(
    private val _children: Iterable<ViewNode>,
    override val info: Info,
    private val imports: Imports
) : ViewNode {

    override val children: Iterable<ViewNode>
        get() = _children.map {
            val width = it.info.width
            val height = it.info.height

            val isWidth0Dp = width is LayoutSizeInDp && width.size == 0
            val isHeight0Dp = height is LayoutSizeInDp && height.size == 0

            it.copyWithInfo(
                layoutWidth = if (isWidth0Dp) EmptyLayoutSize else width,
                layoutHeight = if (isHeight0Dp) EmptyLayoutSize else height
            )
        }


    override fun body(): CodeBlock {
        val instance = imports.viewImports.constraintLayout.root
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

        return CodeBlock.builder()
            .add("%T(%L)", instance, paramCodeBlocks.joinToCode())
            .add(
                CodeBlock.builder()
                    .beginControlFlow(" {")
                    .add(createConstraintRefs())
                    .add(childrenToCodeBlock())
                    .endControlFlow()
                    .build()
            )
            .build()
    }

    private fun createConstraintRefs(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.map { it.info.id.getIdOrDefault() }.filter { it != ViewId.DEFAULT }.forEach {
            codeBlock.addStatement(
                "val ${ViewIdAsVariable(it)}Ref = %M(Any())",
                imports.viewImports.constraintLayout.constrainedLayoutReference
            )
        }
        info.margins.map { it.localVariable() }.filter { it.isNotEmpty() }.toSet().forEach {
            codeBlock.add(it)
            codeBlock.add("\n")
        }
        return codeBlock.build()
    }

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth?,
        layoutHeight: LayoutHeight?
    ): ViewNode {
        return ConstraintLayoutNode(
            _children = children,
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = layoutWidth ?: info.width,
                height = layoutHeight ?: info.height
            ),
            imports = imports
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
        val backgroundColor: ColorAttribute,
        override val id: ViewId,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName> = listOf(),
        val margins: Iterable<ConstraintLayoutMargin>
    ) : ViewInfo

}
