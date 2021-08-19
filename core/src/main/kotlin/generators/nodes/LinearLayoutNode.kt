package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.attributes.colors.ColorAttribute
import poet.addCodeBlockIf
import poet.addCodeIf

class LinearLayoutNode(
    private val info: Info,
    override val children: Iterable<ViewNode>,
    private val _parent: ViewNode?,
) : ViewNode {

    override val parent: ParentViewNode?
        get() = if (_parent != null) ParentViewNode(_parent) else null

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }


    override fun body(): CodeBlock {
        return CodeBlock.builder()
            .addCodeIf(info.orientation == Orientation.Vertical) { "Column" }
            .addCodeIf(info.orientation == Orientation.Horizontal) { "Row" }
            .addCodeIf(info.hasAnyAttribute()) { " (" }
            .addCodeIf(info.hasSeveralAttributes()) {
                "\n\t"
            }
            .addCodeIf(info.backgroundColor.isEmpty().not()) {
                "modifier = Modifier.background(color = ${info.backgroundColor.statement()})"
            }
            .addCodeIf(info.backgroundColor.isEmpty().not() && info.arrangement != Arrangement.NoArrangement) {
                ",\n\t"
            }
            .addCodeIf(info.arrangement != Arrangement.NoArrangement) {
                info.arrangement.statement(info.orientation)
            }
            .addCodeIf(info.hasSeveralAttributes()) { "\n" }
            .addCodeIf(info.hasAnyAttribute()) { ")" }
            .add(" {")
            .addCodeIf(children.iterator().hasNext()) { "\n" }
            .addCodeBlockIf(children.iterator().hasNext()) { childrenToCodeBlock() }
            .addCodeIf(hasAncestors()) { ancestors().joinToString { "\t" } }
            .add("}")
            .addCodeIf(hasAncestors()) { "\n" }
            .build()
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add("\t")
            codeBlock.add(ancestors().joinToString { "\t" })
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    private fun hasAncestors() = ancestors().iterator().hasNext()

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()


    private fun ancestors(): Iterable<ViewNode> {
        return ancestorsIterate(mutableListOf(), this)
    }

    private fun ancestorsIterate(mutableList: MutableList<ViewNode>, viewNode: ViewNode): Iterable<ViewNode> {
        return if (viewNode.parent != null) {
            mutableList.add(viewNode.parent!!)
            ancestorsIterate(mutableList, viewNode.parent!!)
        } else {
            mutableList
        }
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
        val backgroundColor: ColorAttribute
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