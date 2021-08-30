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
        get() = info.textInfo.id

    override fun function(): FunSpec {
        return FunSpec.builder(info.textInfo.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

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
            ChainedMemberName(
                info.constraints.memberNamePrefix,
                info.constraints.codeBlock()
            )
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
        val constraints: Constraints,
        override val height: LayoutHeight,
        override val width: LayoutWidth,
        override val chainedMemberNames: List<ChainedMemberName> = listOf(),
    ) : ViewInfo {
        override val id: String
            get() = textInfo.id
    }
}