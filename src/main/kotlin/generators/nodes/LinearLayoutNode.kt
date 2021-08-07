package generators.nodes

import com.intellij.navigation.areOriginsEqual
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec

class LinearLayoutNode(
    private val info: Info
) : ViewNode {
    override val children: Iterable<ViewNode>
        get() = emptyList()

    override fun generate(): FunSpec {
        return when (info.orientation) {
            Orientation.Horizontal -> FunSpec.builder(info.id)
                .addStatement(
                    """
                    Row { }
                    """.trimIndent()
                ).build()
            Orientation.Vertical -> FunSpec.builder(info.id)
                .addStatement(
                    """
                    Column { }
                    """.trimIndent()
                ).build()
        }
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