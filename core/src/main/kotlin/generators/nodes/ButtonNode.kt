package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.addCodeBlockIf
import poet.addCodeIf

class ButtonNode(
    private val info: Info,
    private val _parent: ViewNode?
) : ViewNode {

    private val textViewNode = TextViewNode(info.textInfo, this)

    override val parent: ParentViewNode?
        get() = if (_parent != null) ParentViewNode(_parent) else null

    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.textInfo.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        return CodeBlock.builder()
            .add("Button(onClick = {}")
            .addCodeIf(info.width.statement().isNotEmpty() || info.height.statement().isNotEmpty()) {
                ", modifier = Modifier.${info.width.statement()}.${info.height.statement()}"
            }
            .add(") {\n")
            .addCodeIf(parent?.hasAncestors()) {
                parent?.ancestors()?.map { "\t" }?.joinToString(separator = "") { it }
            }
            .addCodeIf(info.textInfo.text.isNotEmpty()) { "\t" }
            .addCodeBlockIf(info.textInfo.text.isNotEmpty()) { textViewNode.body() }
            .addCodeIf(parent?.hasAncestors()) {
                parent?.ancestors()?.map { "\t" }?.joinToString(separator = "") { it }
            }
            .addCodeIf(info.textInfo.text.isEmpty()) { "\n" }
            .add("}")
            .addCodeIf(parent?.hasAncestors()) { "\n" }
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


    data class Info(
        val textInfo: TextViewNode.Info,
        val width: LayoutWidth,
        val height: LayoutHeight
    )
}