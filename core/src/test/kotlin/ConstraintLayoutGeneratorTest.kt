import assertions.assertThatAnyFunctionEquals
import org.junit.jupiter.api.Test
import readers.XmlReaderImpl

class ConstraintLayoutGeneratorTest {

    private val xmlReader = XmlReaderImpl()

    @Test
    fun `given no attributes are defined, function should be ConstraintLayout () {}`() {
        val composeGenerator = xmlReader.read(
            content = """ <ConstraintLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |androidx.constraintlayout.compose.ConstraintLayout () {
            |}
            |
            """.trimMargin()
        )
    }

}