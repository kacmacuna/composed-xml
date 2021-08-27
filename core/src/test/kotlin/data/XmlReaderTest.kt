package data

import readers.XmlReader
import readers.XmlReaderImpl

class XmlReaderTest : XmlReader by XmlReaderImpl(false)