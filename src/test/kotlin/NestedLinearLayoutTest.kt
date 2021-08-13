import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class NestedLinearLayoutTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `given TextView is nested inside of Vertical LinearLayout, generated function should be Column {Text(Hello)}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="vertical">
                 <TextView
                    android:id="@+id/title"
                    android:text="Hello"/>
            </LinearLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Column {\n\tText(\"Hello\")\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Column"))
    }

    @Test
    fun `given TextView is nested inside of Horizontal LinearLayout, generated function should be Row {Text(Hello)}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="horizontal">
                 <TextView
                    android:id="@+id/title"
                    android:text="Hello"/>
            </LinearLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Row {\n\tText(\"Hello\")\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
    }

    @Test
    fun `given two TextView is nested inside of LinearLayout, function should be Row {Text(Hello1),nText(Hello2)}`(){
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="horizontal">
                 <TextView
                    android:id="@+id/title"
                    android:text="Hello1"/>
                 <TextView
                    android:id="@+id/title"
                    android:text="Hello2"/>
            </LinearLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Row {\n\tText(\"Hello1\")\n\tText(\"Hello2\")\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
    }

}