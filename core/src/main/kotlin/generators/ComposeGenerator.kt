package generators

import com.squareup.kotlinpoet.FileSpec
import generators.nodes.ViewNode

interface ComposeGenerator {
    fun generate(): FileSpec
}