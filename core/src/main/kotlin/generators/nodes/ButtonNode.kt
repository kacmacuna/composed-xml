package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.elements.colors.ColorElement
import poet.addCodeIf

class ButtonNode(
    private val info: Info,
    private val _parent: ViewNode?
) : ViewNode {

    override val parent: ParentViewNode?
        get() = if (_parent != null) ParentViewNode(_parent) else null

    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {

        return CodeBlock.builder()
            .add("Button(onClick = {}) {\n")
            .addCodeIf(parent?.hasAncestors()) {
                "\t" + parent?.ancestors()?.map { "\t" }?.joinToString(separator = "") { it }
            }
            .addCodeIf(info.text.isNotEmpty()) { "\tText(\"${info.text}\"" }
            .addCodeIf(info.textColor.isEmpty().not()) { ", color = ${info.textColor.statement()}" }
            .addCodeIf(info.text.isNotEmpty()) { ")" }
            .add("\n")
            .addCodeIf(parent?.hasAncestors()) { "\t"+ parent?.ancestors()?.map { "\t" }?.joinToString(separator = "") { it } }
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
            ClassName("androidx.compose.ui.unit", "sp")
        ) + info.textColor.imports()
    }


    data class Info(
        val id: String,
        val text: String,
        val textColor: ColorElement,
        val fontSize: Int
    )
}