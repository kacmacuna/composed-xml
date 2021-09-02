package generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import generators.nodes.ViewNode

class ComposeGeneratorImpl(
    private val viewNode: ViewNode,
    private val fileName: String
) : ComposeGenerator {
    override fun generate(): FileSpec {
        return FileSpec
            .builder(fileName, fileName)
            .addFunction(viewNode.function())
            .build()
    }
}