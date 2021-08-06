package generators

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.Import
import generators.nodes.ViewNode

class ComposeGeneratorImpl(
    private val viewNode: ViewNode,
    private val fileName: String
) : ComposeGenerator {
    override fun generate(): FileSpec {
        return FileSpec
            .builder(fileName, fileName)
            .addFunction(viewNode.generate())
            .also { builder -> viewNode.imports().forEach { builder.addImport(it, "") } }
            .build()
    }
}