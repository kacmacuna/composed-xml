import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class LinearLayoutComposeGeneratorTest {


    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `given layout is vertical generated function should include Column`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="vertical" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Column {}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Column"))
    }

    @Test
    fun `given layout is horizontal generated function should include Row`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="horizontal" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """Row {}""".trimIndent()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
    }

}