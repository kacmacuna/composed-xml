package readers

import generators.ComposeGenerator
import generators.ComposeGeneratorImpl
import org.xml.sax.InputSource
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
        return when (document.documentElement.nodeName) {
            "TextView" -> ComposeGeneratorImpl(
                TextViewReader(LayoutElement(document.documentElement)).node(),
                fileName
            )
            "LinearLayout" -> ComposeGeneratorImpl(
                LinearLayoutReader(LayoutElement(document.documentElement)).node(),
                fileName
            )
            else -> TODO()
        }
    }
}