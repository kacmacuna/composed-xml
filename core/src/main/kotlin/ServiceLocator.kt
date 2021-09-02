import readers.imports.Imports

class ServiceLocator private constructor(
    val imports: Imports
) {

    companion object {
        private var INSTANCE: ServiceLocator? = null
        fun createInstance(
            imports: Imports,
        ) {
            INSTANCE = ServiceLocator(imports)
        }

        fun get(): ServiceLocator {
            return INSTANCE ?: error("ServiceLocator have not been initialized")
        }
    }

}