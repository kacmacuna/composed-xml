import assertions.assertThatAnyFunctionEquals
import com.squareup.kotlinpoet.FunSpec
import data.XmlReaderTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

class LinearLayoutComposeGeneratorTest {


    private val xmlReader: XmlReader = XmlReaderTest()

    @Test
    fun `given layout is vertical, function should include Column`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="vertical" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals("Column () {\n}\n")
    }

    @Test
    fun `given layout is horizontal, function should include Row`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="horizontal" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals("""
            |Row () {
            |}
            |""".trimIndent().trimMargin())
    }

    @Test
    fun `given layout has green background, function should be Row(background(green)){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:background="@color/green" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
                |Row (modifier = Modifier.background(colorResource(R.color.green))) {
                |}
                |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given layout has end gravity, function should be Row(Arrangement,End){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:gravity="end" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |Row (horizontalArrangement = Arrangement.End) {
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given vertical layout has end gravity, function should be Column(Arrangement,End){}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:gravity="end"
                android:orientation="vertical" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |Column (verticalArrangement = Arrangement.End) {
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given layout has end gravity and green background, function should be Column(background(green), Arrangement,End)`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/title"
                android:gravity="end"
                android:background="@color/green"
                android:orientation="vertical" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |Column (modifier = Modifier.background(colorResource(R.color.green)), verticalArrangement = Arrangement.End) {
            |}
            |
        """.trimIndent().trimMargin()
        )
    }

}