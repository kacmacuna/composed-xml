package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.attributes.colors.ColorAttribute

class TextViewNode(
    private val info: Info,
    private val _parent: ViewNode?
) : ViewNode {

    override val parent: ParentViewNode?
        get() = if (_parent != null) ParentViewNode(_parent) else null

    override val children: Iterable<ViewNode> = emptyList()

    override fun function(): FunSpec {
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addCode(body())
            .build()
    }

    override fun body(): CodeBlock {
        val mainStatementBuilder = StringBuilder()
        mainStatementBuilder.append("""Text("${info.text}"""")
        if (info.textColor.isEmpty().not()) {
            mainStatementBuilder.append(""", color = ${info.textColor.statement()}""")
        }
        if (info.fontSize > 0) {
            mainStatementBuilder.append(""", fontSize = ${info.fontSize}.sp""")
        }
        mainStatementBuilder.append(')')

        return CodeBlock.builder()
            .addStatement(mainStatementBuilder.toString())
            .build()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.material", "Text"),
            ClassName("androidx.compose.ui.unit", "sp")
        ) + info.textColor.imports()
    }


    data class Info(
        val id: String,
        val text: String,
        val textColor: ColorAttribute,
        val fontSize: Int
    )

}