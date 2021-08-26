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

        val expectedBody = """
            |Button(onClick = {}) {
            |}
        """.trimIndent().trimMargin()

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

        val expectedBody = """
            |Button(onClick = {}) {
            |  androidx.compose.material.Text("Hello", color = colorResource(R.color.blue))
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(
            importsAsStrings, CoreMatchers.hasItems(
                "androidx.compose.material.Button",
                "androidx.compose.ui.res.colorResource",
                "androidx.compose.material.Text",
                )
        )
    }

    @Test
    fun `given button defines font size, function should be Button{Text(fontSize = 20,sp)}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <Button 
                android:id="@+id/title"
                android:text="Hello"
                android:textSize="20sp"/>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody ="""
            |Button(onClick = {}) {
            |  androidx.compose.material.Text("Hello", fontSize = 20.sp)
            |}
        """.trimIndent().trimMargin()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `given button defines W wrap_content and H match_parent function should be Text(wrapContentWidth(),fillMaxHeight())`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <Button
                android:id="@+id/title"
                android:layout_width="wrap_content"
                android:layout_height="match_parent" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            |Button(onClick = {}, modifier = Modifier.wrapContentWidth().fillMaxHeight()) {
            |}
        """.trimIndent().trimMargin()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

    @Test
    fun `given button defines W 20dp and H 30dp function should be Text(modifier,width(20dp),height(30dp))`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <Button
                android:id="@+id/title"
                android:layout_width="20dp"
                android:layout_height="30dp" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            |Button(onClick = {}, modifier = Modifier.width(20.dp).height(30.dp)) {
            |}
        """.trimIndent().trimMargin()

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

}