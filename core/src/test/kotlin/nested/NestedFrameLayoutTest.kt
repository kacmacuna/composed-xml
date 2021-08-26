package nested

import assertions.assertThatAnyFunctionEquals
import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl
import kotlin.math.exp

class NestedFrameLayoutTest {

    private val xmlReader: XmlReader = XmlReaderImpl()

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
            |androidx.compose.foundation.layout.Box (contentAlignment = Box.Alignment.Center) {
            |  androidx.compose.material.Text("Hello")
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
            |androidx.compose.foundation.layout.Box (contentAlignment = Box.Alignment.BottomCenter) {
            |  androidx.compose.foundation.layout.Box (contentAlignment = Box.Alignment.CenterEnd) {
            |    androidx.compose.material.Button(onClick = {}) {
            |      androidx.compose.material.Text("Hello")
            |    }
            |  }
            |}
            |""".trimIndent().trimMargin()


        file assertThatAnyFunctionEquals expectedBody
    }

}