import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class FrameLayoutGeneratorTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `given FrameLayout, function should be Box{}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box () {\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Box"))
    }

    @Test
    fun `given FrameLayout with gravity left,center, function should be Box(TopStart){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout 
                android:id="@+id/content"
                android:gravity="top|start"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (contentAlignment = Box.Alignment.TopStart) {\n}"

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `given FrameLayout with background blue, function should be Box(background(blue)){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout 
                android:id="@+id/content"
                android:background="@color/blue"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (modifier = Modifier.background(colorResource(R.color.blue))) {\n}"

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `given FrameLayout with gravity start bkg blue, function should be Box(background(blue), Left){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout 
                android:id="@+id/content"
                android:gravity="start"
                android:background="@color/blue"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (modifier = Modifier.background(colorResource(R.color.blue)), contentAlignment = Box.Alignment.Start) {\n}"

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

}