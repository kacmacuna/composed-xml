package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.elements.colors.ColorElement
import poet.addCodeIf

class ButtonNode(
    private val info: Info,
    override val parent: ViewNode?
) : ViewNode {

    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val mainStatementBuilder = StringBuilder()

        if (info.text.isNotEmpty())
            mainStatementBuilder.append("""Text("${info.text}"""")
        if (info.textColor.isEmpty().not()) {
            mainStatementBuilder.append(""", color = ${info.textColor.statement()}""")
        }
        if (info.fontSize > 0) {
            mainStatementBuilder.append(""", fontSize = ${info.fontSize}.sp""")
        }

        mainStatementBuilder.append("\n}")

        return CodeBlock.builder()
            .add("Button(onClick = {}) {\n")
            .addCodeIf(info.text.isNotEmpty()) { "\tText(\"${info.text}\"" }
            .addCodeIf(info.textColor.isEmpty().not()) { ", color = ${info.textColor.statement()}" }
            .addCodeIf(info.text.isNotEmpty()) { ")" }
            .add("\n}")
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