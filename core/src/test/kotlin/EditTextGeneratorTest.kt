import assertions.assertThatAnyFunctionEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class EditTextGeneratorTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `given nothing is defined, function should be TextField(_, onValueChange = {})`() {
        val composeGenerator = xmlReader.read(
            content = """ <EditText /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals("""TextField(value = "", onValueChange = {})""")
    }

    @Test
    fun `given green background, function should be TextField(modifier = background(green), DOTS)`() {
        val composeGenerator = xmlReader.read(
            content = """ 
                <EditText
                 android:background="@color/green" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |TextField(value = "", modifier = Modifier.background(colorResource(R.color.green)), onValueChange = {})
            """.trimMargin().trimIndent()
        )
    }

    @Test
    fun `given green bkg and 1F weight, function should be TextField(modifier = bkg(green),weight(1F))`() {
        val composeGenerator = xmlReader.read(
            content = """ 
                <EditText
                 android:background="@color/green"
                 android:weight="1F" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |TextField(value = "", modifier = Modifier.background(colorResource(R.color.green)).weight(1.0F), onValueChange = {})
            """.trimMargin().trimIndent()
        )
    }

    @Test
    fun `given W wrap_content and H match_parent, function should be TextField(wrapContentWidth(),fillMaxHeight())`(){
        val composeGenerator = xmlReader.read(
            content = """ 
                <EditText
                 android:layout_width="wrap_content"
                 android:layout_height="match_parent" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |TextField(value = "", modifier = Modifier.wrapContentWidth().fillMaxHeight(), onValueChange = {})
            """.trimMargin().trimIndent()
        )
    }

}