import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class ButtonComposeGeneratorTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `given layouts root view is Button, function should be Button{}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <Button android:id="@+id/title" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Button(onClick = {}) {\n\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.material.Button"))
    }

    @Test
    fun `given Button defines text and textColor, function should be Button{Text(Hello, colorResource(R,color,blue)}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <Button 
                android:id="@+id/title"
                android:text="Hello"
                android:textColor="@color/blue"/>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Button(onClick = {}) {\n\tText(\"Hello\", color = colorResource(R.color.blue))\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(
            importsAsStrings, CoreMatchers.hasItems(
                "androidx.compose.material.Button",
                "androidx.compose.ui.res.colorResource",
            )
        )
    }

}