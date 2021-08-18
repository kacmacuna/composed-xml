package generators

import com.squareup.kotlinpoet.FileSpec

object EmptyComposeGenerator : ComposeGenerator {
    override fun generate(): FileSpec {
        return FileSpec.builder("", "").build()
    }
}