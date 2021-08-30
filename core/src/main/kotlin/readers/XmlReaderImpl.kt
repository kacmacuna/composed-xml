package readers

import generators.ComposeGenerator
import generators.ComposeGeneratorImpl
import generators.EmptyComposeGenerator
import org.xml.sax.InputSource
import org.xml.sax.SAXParseException
import readers.imports.Imports
import readers.tags.ViewTags
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


class XmlReaderImpl(
    includePackageNames: Boolean = true
) : XmlReader {

    private val imports = Imports.Impl(includePackageNames)

    init {
        ServiceLocator.createInstance(
            imports = imports
        )
    }

    override fun read(content: ByteArray, fileName: String): ComposeGenerator {
        return read(String(content), fileName)
    }

    override fun read(content: String, fileName: String): ComposeGenerator {
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val document = try {
            db.parse(InputSource(StringReader(content)))
        } catch (ex: SAXParseException) {
            return EmptyComposeGenerator
        }
        return ComposeGeneratorImpl(
            ViewTags.fromString(document.documentElement.nodeName).toLayoutElement(
                document.documentElement,
                imports = imports
            ).node(),
            fileName
        )
    }
}
