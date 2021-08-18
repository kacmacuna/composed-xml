package generators.nodes.attributes.colors

import com.squareup.kotlinpoet.ClassName

class ColorAttributeResource(
    private val input: String
) : ColorAttribute {

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