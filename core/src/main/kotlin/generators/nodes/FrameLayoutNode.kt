package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import poet.addCodeBlockIf
import poet.addCodeIf

class FrameLayoutNode(
    override val children: Iterable<ViewNode>,
    private val info: Info,
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
            .add("Box")
            .addCodeIf(info.alignment != Alignment.NoAlignment) {
                " (contentAlignment = Alignment.${info.alignment.name})"
            }.add(" {\n")
            .addCodeBlockIf(children.iterator().hasNext()) { childrenToCodeBlock() }
            .addCodeIf(parent?.hasAncestors()) { parent?.ancestors()?.joinToString { "\t" } }
            .addCodeIf(children.iterator().hasNext().not()) { "\n" }
            .add("}")
            .addCodeIf(parent?.hasAncestors()) { "\n" }
            .build()
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(ClassName("androidx.compose.foundation.layout", "Box")) + children.map { it.imports() }.flatten()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add("\t")
            parent?.ancestors()?.joinToString { "\t" }?.let { it1 -> codeBlock.add(it1) }
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    class Info(
        val id: String,
        val alignment: Alignment
    )

    enum class Alignment {
        TopStart,
        TopCenter,
        TopEnd,
        CenterStart,
        Center,
        CenterEnd,
        BottomStart,
        BottomCenter,
        BottomEnd,

        Top,
        CenterVertically,
        Bottom,

        Start,
        CenterHorizontally,
        End,

        NoAlignment;
    }

}