package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.EmptyLayoutSize
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberName
import readers.imports.Imports

class LinearLayoutNode(
    private val info: Info,
    private val imports: Imports,
    override val children: Iterable<ViewNode>,
) : ViewNode {
    override val id: String
        get() = info.id


    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }


    override fun body(): CodeBlock {
        val instance = when (info.orientation) {
            Orientation.Horizontal -> imports.viewImports.row
            Orientation.Vertical -> imports.viewImports.column
        }
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
                containsArguments = true
            ),
            ChainedMemberName(
                prefix = info.height.prefix(),
                info.height.argument(),
                containsArguments = true
            )

        ).codeBlock()
        if (modifiers.isNotEmpty()) paramCodeBlocks.add(modifiers)

        if (info.arrangement != Arrangement.NoArrangement) {
            paramCodeBlocks.add(CodeBlock.of(info.arrangement.statement(info.orientation)))
        }

        return CodeBlock.builder()
            .add("%T (%L)", instance, paramCodeBlocks.joinToCode())
            .add(
                CodeBlock.builder()
                    .beginControlFlow(" {")
                    .add(childrenToCodeBlock())
                    .endControlFlow()
                    .build()
            ).build()
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }


    override fun imports(): Iterable<ClassName> {
        return children.map { it.imports() }.flatten().toSet()
    }

    override fun copyWithInfo(
        vararg chainedMemberNames: ChainedMemberName,
        layoutWidth: LayoutWidth,
        layoutHeight: LayoutHeight
    ): ViewNode {
        return LinearLayoutNode(
            info = info.copy(
                chainedMemberNames = info.chainedMemberNames + chainedMemberNames,
                width = if (layoutWidth != EmptyLayoutSize) layoutWidth else info.width,
                height = if (layoutHeight != EmptyLayoutSize) layoutHeight else info.height
            ),
            imports = imports,
            children = children
        )
    }

    data class Info(
        override val id: String,
        val orientation: Orientation,
        val arrangement: Arrangement,
        val backgroundColor: ColorAttribute,
        override val width: LayoutWidth,
        override val height: LayoutHeight,
        override val chainedMemberNames: List<ChainedMemberName> = listOf()
    ) : ViewInfo

    enum class Orientation { Horizontal, Vertical; }
    enum class Arrangement {
        Start, End, Center, CenterHorizontal, CenterVertical, Top, Bottom, NoArrangement;


        fun statement(orientation: Orientation): String {
            return if (this == CenterHorizontal)
                "horizontalArrangement = Arrangement.Center"
            else if (this == CenterVertical)
                "verticalArrangement  = Arrangement.Center"
            else when (orientation) {
                Orientation.Horizontal -> "horizontalArrangement = Arrangement.$name"
                Orientation.Vertical -> "verticalArrangement = Arrangement.$name"
            }
        }
    }
}