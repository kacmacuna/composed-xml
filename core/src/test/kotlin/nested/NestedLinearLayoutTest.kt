package nested

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
    fun `given Text is nested inside of vLinearLayout, generated function should be Column {Text(Hello)}`() {
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

        val expectedBody = """
            |Column () {
            |  Text("Hello")
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Column"))
    }

    @Test
    fun `given Text is nested inside of hLinearLayout, generated function should be Row {Text(Hello)}`() {
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

        val expectedBody = """
            |Row () {
            |  Text("Hello")
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
    }

    @Test
    fun `given two Text is nested inside of LinearLayout, function should be Row {Text(Hello1),nText(Hello2)}`() {
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

        val expectedBody = """
            |Row () {
            |  Text("Hello1")
            |  Text("Hello2")
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
    }

    @Test
    fun `given Text is nested in hLinearLayout and it is nested in vLinearLayout, function should be Column{Row{Text(Hello)}}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/vertical"
                android:orientation="vertical">
                
                <LinearLayout
                android:id="@+id/horizontal"
                android:orientation="horizontal">
                
                    <TextView
                        android:id="@+id/title"
                        android:text="Hello"/>
                        
                </LinearLayout>
                
            </LinearLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            |Column () {
            |  Row () {
            |    Text("Hello")
            |  }
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())

        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Column"))
    }

    @Test
    fun `given Button is nested in hLinearLayout and it is nested in vLinearLayout, function should be Column{Row{Text(Hello)}}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <LinearLayout
                android:id="@+id/vertical"
                android:orientation="vertical">
                
                <LinearLayout
                android:id="@+id/horizontal"
                android:orientation="horizontal">
                
                    <Button
                        android:id="@+id/title"
                        android:text="Hello"/>
                        
                </LinearLayout>
                
            </LinearLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = """
            |Column () {
            |  Row () {
            |    Button(onClick = {}) {
            |      Text("Hello")
            |    }
            |  }
            |}
        """.trimIndent().trimMargin()

        val importsAsStrings = file.toBuilder().imports.map { it.toString() }

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())

        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Row"))
        MatcherAssert.assertThat(importsAsStrings, CoreMatchers.hasItems("androidx.compose.foundation.layout.Column"))
    }

}