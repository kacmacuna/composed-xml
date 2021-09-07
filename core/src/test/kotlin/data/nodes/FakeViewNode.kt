package data.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.joinToCode
import generators.nodes.ViewId
import generators.nodes.ViewInfo
import generators.nodes.ViewNode
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName

class FakeViewNode(
    override val info: FakeInfo
) : ViewNode {
    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun body(): CodeBlock {
        val instance = ClassName("", "FakeView")
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        paramCodeBlocks.add(CodeBlock.of("onClick = {}"))

        val modifierCodeBlock = ChainedCodeBlock(
            prefixNamedParam = "modifier",
            prefix = ClassName("", "Modifier"),
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
            .add("\n")
            .build()
    }

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth?,
        layoutHeight: LayoutHeight?
    ): ViewNode {
        return FakeViewNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = layoutWidth ?: info.width,
                height = layoutHeight ?: info.height
            ),
        )
    }

    data class FakeInfo(
        override val width: LayoutWidth = EmptyLayoutSize,
        override val height: LayoutHeight = EmptyLayoutSize,
        override val chainedMemberNames: List<ChainedMemberName> = emptyList()
    ) : ViewInfo {
        override val id: ViewId
            get() = ViewId("fake_id")

    }

}