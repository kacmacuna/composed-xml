package generators.nodes

import com.squareup.kotlinpoet.*
import generators.nodes.attributes.colors.ColorAttribute
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addComposeAnnotation
import poet.chained.ChainedCodeBlock
import poet.chained.ChainedMemberCall

class LinearLayoutNode(
    private val info: Info,
    override val children: Iterable<ViewNode>,
) : ViewNode {


    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addComposeAnnotation()
            .addCode(body())
            .build()
    }


    override fun body(): CodeBlock {
        val instance = ClassName(
            "", when (info.orientation) {
                Orientation.Horizontal -> "Row"
                Orientation.Vertical -> "Column"
            }
        )
        val paramCodeBlocks = mutableListOf<CodeBlock>()
        val modifiers = ChainedCodeBlock(
            "modifier = Modifier.",
            ChainedMemberCall("background", info.backgroundColor.statement()),
            ChainedMemberCall(info.width.statement(), "", true),
            ChainedMemberCall(info.height.statement(), "", true)

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
        return (when (info.orientation) {
            Orientation.Horizontal -> listOf(ClassName("androidx.compose.foundation.layout", "Row"))
            Orientation.Vertical -> listOf(ClassName("androidx.compose.foundation.layout", "Column"))
        } + children.map { it.imports() }.flatten()).toSet()
    }

    class Info(
        val id: String,
        val orientation: Orientation,
        val arrangement: Arrangement,
        val backgroundColor: ColorAttribute,
        val width: LayoutWidth,
        val height: LayoutHeight,
        val weight: Float
    ) {
        fun hasAnyAttribute(): Boolean {
            return arrangement != Arrangement.NoArrangement || backgroundColor.isEmpty().not()
        }

        fun hasSeveralAttributes(): Boolean {
            return backgroundColor.isEmpty().not() && arrangement != Arrangement.NoArrangement
        }
    }

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