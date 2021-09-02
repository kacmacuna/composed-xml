import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import readers.generator.NumGenerator
import readers.imports.AttributeImports
import readers.imports.Imports

class ServiceLocator private constructor(
    val imports: Imports,
    val numGenerator: NumGenerator
) {

    companion object {
        private var INSTANCE: ServiceLocator? = null
        fun createInstance(
            imports: Imports,
            numGenerator: NumGenerator
        ) {
            INSTANCE = ServiceLocator(imports, numGenerator)
        }

        fun get(): ServiceLocator {
            return INSTANCE ?: error("ServiceLocator have not been initialized")
        }
    }

}