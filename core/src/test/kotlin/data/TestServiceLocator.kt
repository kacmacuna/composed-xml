package data

import readers.generator.NumGenerator
import readers.imports.Imports

fun createFakeServiceLocator() {
    ServiceLocator.createInstance(Imports.Impl(false), object : NumGenerator {
        override fun generate(): Int {
            return 1
        }

    })
}