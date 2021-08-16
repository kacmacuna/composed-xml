package readers

import generators.ComposeGenerator
import generators.ComposeGeneratorImpl
import org.xml.sax.InputSource
import readers.elements.ButtonElement
import readers.elements.LayoutElement
import readers.elements.LinearLayoutElement
import readers.elements.TextViewElement
import readers.tags.ViewTags
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


class XmlReaderImpl : XmlReader {
    override fun read(content: ByteArray, fileName: String): ComposeGenerator {
        return read(String(content), fileName)
    }

    override fun read(content: String, fileName: String): ComposeGenerator {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val document = db.parse(InputSource(StringReader(content)))
        return ComposeGeneratorImpl(
            ViewTags.fromString(document.documentElement.nodeName).toLayoutElement(
                document.documentElement, null
            ).node(),
            fileName
        )
    }
}
