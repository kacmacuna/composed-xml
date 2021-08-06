import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import plugin.XmlNameToComposableName

class XmlNameToComposableNameTest {

    private val xmlNameToComposableName = XmlNameToComposableName()

    @Test
    fun `activity_main,xml equals to ActivityMainComposable,kt`(){
        val result = xmlNameToComposableName.translate("activity_main.xml")
        val expected = "ActivityMainComposable.kt"

        assertEquals(expected, result)
    }

}