import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class TextViewComposeGeneratorTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

    @Test
    fun `generated file's name should be test`() {
        val composeGenerator = xmlReader.read(
            content = """ <TextView /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        Assertions.assertEquals("test", file.name)
    }

    @Test
    fun `generated function's name should be Title`() {
        val composeGenerator = xmlReader.read(
            content = """ <TextView android:id="@+id/title"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        Assertions.assertEquals("Title", titleFunction.name)
    }

    @Test
    fun `generated function should be @Composable`() {
        val composeGenerator = xmlReader.read(
            content = """ <TextView android:id="@+id/title"/> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        Assertions.assertEquals(
            "androidx.compose.runtime.Composable",
            titleFunction.annotations.first().typeName.toString()
        )
    }

    @Test
    fun `generated function should contain blue text, size of 20 and text Hello`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <TextView
                android:id="@+id/title"
                android:text="Hello"
                android:textColor="@color/blue"
                android:textSize="20sp" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            Text("Hello", color = colorResource(R.color.blue), fontSize = 20.sp)
        """.trimIndent()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `generated function should import Text and colorResource`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <TextView
                android:id="@+id/title"
                android:text="Hello"
                android:textColor="@color/blue"
                android:textSize="20sp" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        assertThat(
            importsAsStrings, CoreMatchers.hasItems(
                "androidx.compose.material.Text",
                "androidx.compose.ui.res.colorResource"
            )
        )

    }

    @Test
    fun `generated function should have hardcoded color value`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <TextView
                android:id="@+id/title"
                android:text="Hello"
                android:textColor="#FF11FF"
                android:textSize="20sp" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            Text("Hello", color = Color(android.graphics.Color.parseColor("#FF11FF")), fontSize = 20.sp)
        """.trimIndent()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `generated function should not define text color and text size`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <TextView
                android:id="@+id/title"
                android:text="Hello" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            Text("Hello")
        """.trimIndent()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

}