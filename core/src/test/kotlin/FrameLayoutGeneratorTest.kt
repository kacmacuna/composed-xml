import assertions.assertThatAnyFunctionEquals
import com.squareup.kotlinpoet.FunSpec
import data.XmlReaderTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class FrameLayoutGeneratorTest {

    private val xmlReader: XmlReader = XmlReaderTest()

    @Test
    fun `given FrameLayout, function should be Box{}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            "Box () {\n}\n"
        )
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

        file.assertThatAnyFunctionEquals(
            "Box (contentAlignment = Box.Alignment.TopStart) {\n}\n"
        )
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

        file.assertThatAnyFunctionEquals(
            "Box (modifier = Modifier.background(colorResource(R.color.blue))) {\n}\n"
        )
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

        file.assertThatAnyFunctionEquals(
            "Box (modifier = Modifier.background(colorResource(R.color.blue))," +
                    " contentAlignment = Box.Alignment.Start) {\n}\n"
        )
    }

}