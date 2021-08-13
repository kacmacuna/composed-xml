package generators.nodes

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import poet.addCodeBlockIf
import poet.addCodeIf

class LinearLayoutNode(
    private val info: Info,
    override val children: Iterable<ViewNode>,
) : ViewNode {

    override fun generate(): FunSpec {
        return when (info.orientation) {
            Orientation.Horizontal -> FunSpec.builder(info.id)
                .addCode(CodeBlock.builder().add("Row {").build())
                .addCodeIf(children.iterator().hasNext()) { "\n" }
                .addCodeBlockIf(children.iterator().hasNext()) { childrenToCodeBlock() }
                .addCode("}")
                .build()
            Orientation.Vertical -> FunSpec.builder(info.id)
                .addCode(CodeBlock.builder().add("Column {").build())
                .addCodeIf(children.iterator().hasNext()) { "\n" }
                .addCodeBlockIf(children.iterator().hasNext()) { childrenToCodeBlock() }
                .addCode("}")
                .build()
        }
    }

    private fun childrenToCodeBlock(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        children.forEach {
            codeBlock.add("\t")
            codeBlock.add(it.body())
        }
        return codeBlock.build()
    }

    override fun body(): CodeBlock {
        return CodeBlock.builder().build()
    }

    override fun imports(): Iterable<ClassName> {
        return when (info.orientation) {
            Orientation.Horizontal -> listOf(ClassName("androidx.compose.foundation.layout", "Row"))
            Orientation.Vertical -> listOf(ClassName("androidx.compose.foundation.layout", "Column"))
        }
    }

    class Info(
        val id: String,
        val orientation: Orientation
    )

    enum class Orientation { Horizontal, Vertical; }
}