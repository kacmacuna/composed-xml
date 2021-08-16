package readers

import generators.ComposeGenerator

interface XmlReader {
    fun read(content: ByteArray, fileName: String) : ComposeGenerator
    fun read(content: String, fileName: String) : ComposeGenerator
}