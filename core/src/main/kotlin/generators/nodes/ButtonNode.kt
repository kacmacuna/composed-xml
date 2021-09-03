package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.constraints.Constraints
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addCodeBlockIf
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ButtonNode(
    private val info: Info,
    private val imports: Imports
) : ViewNode {

    private val textViewNode = TextViewNode(info.textInfo, imports)


    override val children: Iterable<ViewNode>
        get() = emptyList()
    override val id: String
        get() = info.id.getIdOrDefault()


    override fun body(): CodeBlock {
        val instance = imports.viewImports.button
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("onClick = {}"))

        val modifierCodeBlock = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = imports.attributeImports.modifier,
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

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth,
        layoutHeight: LayoutHeight
    ): ViewNode {
        return ButtonNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = if (layoutWidth != EmptyLayoutSize) layoutWidth else info.width,
                height = if (layoutHeight != EmptyLayoutSize) layoutHeight else info.height
            ),
            imports = imports
        )
    }


    data class Info(
        val textInfo: TextViewNode.Info,
        override val height: LayoutHeight,
        override val width: LayoutWidth,
        override val chainedMemberNames: List<ChainedMemberName> = listOf(),
    ) : ViewInfo {
        override val id: ViewId
            get() = textInfo.id
    }
}