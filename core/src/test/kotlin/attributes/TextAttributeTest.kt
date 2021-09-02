package attributes

import data.createFakeServiceLocator
import generators.nodes.attributes.TextAttribute
import generators.nodes.attributes.size.DPAttribute
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import readers.imports.Imports

class TextAttributeTest {

    @BeforeEach
    fun setUp() {
        createFakeServiceLocator()
    }

    @Test
    fun `given attributeValue is @string hello code block should be stringResource(R,string,hello)`() {
        val dpAttribute = TextAttribute("@string/hello")

        Assertions.assertEquals("stringResource(id = R.string.hello)", dpAttribute.statement().toString())
    }

    @Test
    fun `given attributeValue is Hello code block should be Hello`() {
        val dpAttribute = TextAttribute("Hello")

        Assertions.assertEquals("\"Hello\"", dpAttribute.statement().toString())
    }

}