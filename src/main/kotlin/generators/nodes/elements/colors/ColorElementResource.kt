package generators.nodes.elements.colors

import com.squareup.kotlinpoet.ClassName

class ColorElementResource(
    private val input: String
) : ColorElement {

    override fun statement(): String {
        val textColor = input.removePrefix("@color/")
        return "colorResource(R.color.$textColor)"
    }

    override fun imports(): Iterable<ClassName> {
        return listOf(ClassName("androidx.compose.ui.res",  "colorResource"))
    }

    override fun isEmpty(): Boolean {
        return false
    }

}