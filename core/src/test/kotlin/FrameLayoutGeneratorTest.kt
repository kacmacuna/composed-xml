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
    fun `given layouts root view is FrameLayout, function should be Box{}`(){
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box {\n\n}"

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Box"))
    }

    @Test
    fun `given layouts root view is FrameLayout with gravity left,center, function should be Box(TopStart){}`(){
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout 
                android:id="@+id/content"
                android:gravity="top|start"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (contentAlignment = Alignment.TopStart) {\n\n}"

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

}