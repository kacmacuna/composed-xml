package generators.nodes

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.elements.colors.ColorElement

//Text("${info.text}", color = colorResource(${info.textColorRes}), fontSize = ${info.fontSize}.sp)
class TextViewNode(
    private val info: Info
) : ViewNode {
    override val children: Iterable<ViewNode> = emptyList()

    override fun generate(): FunSpec {
        val mainStatementBuilder = StringBuilder()
        mainStatementBuilder.append("""Text("${info.text}"""")
        if (info.textColor.isEmpty().not()) {
            mainStatementBuilder.append(""", color = ${info.textColor.statement()}""")
        }
        if (info.fontSize > 0) {
            mainStatementBuilder.append(""", fontSize = ${info.fontSize}.sp""")
        }
        mainStatementBuilder.append(')')
        return FunSpec.builder(info.id)
            .addAnnotation(composeAnnotation())
            .addStatement(mainStatementBuilder.toString())
            .build()
    }

    private fun composeAnnotation() = AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()

    override fun imports(): Iterable<ClassName> {
        return listOf(
            ClassName("androidx.compose.material", "Text"),
            ClassName("androidx.compose.ui.res", "colorResource")
        ) + info.textColor.imports()
    }


    data class Info(
        val id: String,
        val text: String,
        val textColor: ColorElement,
        val fontSize: Int
    )

}