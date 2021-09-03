package generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import generators.nodes.ViewNode
import poet.addComposeAnnotation

class ComposeGeneratorImpl(
    private val viewNode: ViewNode,
    private val fileName: String
) : ComposeGenerator {
    override fun generate(): FileSpec {
        return FileSpec
            .builder(fileName, fileName)
            .addFunction(func())
            .build()
    }

    private fun func(): FunSpec {
        return FunSpec.builder(viewNode.id)
            .addComposeAnnotation()
            .addCode(viewNode.body())
            .build()
    }
}