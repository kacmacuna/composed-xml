package nested

import assertions.assertThatAnyFunctionEquals
import com.squareup.kotlinpoet.FunSpec
import data.XmlReaderTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl
import kotlin.math.exp

class NestedFrameLayoutTest {

    private val xmlReader: XmlReader = XmlReaderTest()

    @Test
    fun `given Text is nested inside of centered FrameLayout, generated function should be Box(Center) {Text(Hello)}`(){
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout
                android:id="@+id/title"
                android:gravity="center">
                 <TextView
                    android:id="@+id/title"
                    android:text="Hello"/>
            </FrameLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        val expectedBody = """
            |Box (contentAlignment = Box.Alignment.Center) {
            |  Text("Hello")
            |}
            |
        """.trimIndent().trimMargin()

        file assertThatAnyFunctionEquals expectedBody
    }

    @Test
    fun `given Button is nested in bcFrameLayout and it is nested in ceFrameLayout, function should be Column{Row{Text(Hello)}}`() {
        val composeGenerator = xmlReader.read(
            content = """ 
            <FrameLayout
                android:id="@+id/vertical"
                android:gravity="bottom|center">
                
                <FrameLayout
                    android:id="@+id/horizontal"
                    android:gravity="center|end">
                
                    <Button
                        android:id="@+id/title"
                        android:text="Hello"/>
                        
                </FrameLayout>
                
            </FrameLayout>""".trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        val expectedBody = """
            |Box (contentAlignment = Box.Alignment.BottomCenter) {
            |  Box (contentAlignment = Box.Alignment.CenterEnd) {
            |    Button(onClick = {}) {
            |      Text("Hello")
            |    }
            |  }
            |}
            |""".trimIndent().trimMargin()


        file assertThatAnyFunctionEquals expectedBody
    }

}