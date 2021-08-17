package nested

import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import readers.XmlReader
import readers.XmlReaderImpl

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
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (contentAlignment = Alignment.Center) {\n\tText(\"Hello\")\n}"

        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
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
        val titleFunction = file.members.first { it is FunSpec } as FunSpec

        val expectedBody = "Box (contentAlignment = Alignment.BottomCenter) {\n\tBox (contentAlignment = Alignment.CenterEnd) {\n\t\tButton(onClick = {}) {\n\t\t\tText(\"Hello\")\n\t\t}\n\t}\n}"


        Assertions.assertEquals(expectedBody, titleFunction.body.toString().trim())
    }

}