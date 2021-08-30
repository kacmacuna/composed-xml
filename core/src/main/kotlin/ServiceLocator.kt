import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import readers.imports.AttributeImports
import readers.imports.Imports

class ServiceLocator(
    val imports: Imports
) {

    companion object {
        private var INSTANCE: ServiceLocator? = null
        fun createInstance(
            imports: Imports
        ) {
            INSTANCE = ServiceLocator(imports)
        }

        fun get(): ServiceLocator {
            return INSTANCE ?: error("CoreEngine have not been initialized")
        }
    }

}