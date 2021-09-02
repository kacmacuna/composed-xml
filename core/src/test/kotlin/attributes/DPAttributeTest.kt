package attributes

import data.createFakeServiceLocator
import generators.nodes.attributes.size.DPAttribute
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import readers.imports.Imports

class DPAttributeTest {

    @BeforeEach
    fun setUp() {
        createFakeServiceLocator()
    }

    @Test
    fun `given attributeValue is @dimen dp_24 code block should be dimenResource(R,dimen,dp_24)`() {
        val dpAttribute = DPAttribute("@dimen/dp_24")

        Assertions.assertEquals("dimensionResource(id = R.dimen.dp_24)", dpAttribute.statement().toString())
    }

    @Test
    fun `given attributeValue is 24dp code block should be 24,dp`() {
        val dpAttribute = DPAttribute("24dp")

        Assertions.assertEquals("24.dp", dpAttribute.statement().toString())
    }

}