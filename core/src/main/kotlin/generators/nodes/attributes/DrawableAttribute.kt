package generators.nodes.attributes

import com.squareup.kotlinpoet.CodeBlock

class DrawableAttribute(private val attribute: String) {

    fun statement(): CodeBlock {
        return CodeBlock.of(
            "%M(R.drawable.${attribute.removePrefix("@drawable/")})",
            ServiceLocator.get().imports.attributeImports.painterResource
        )
    }

}