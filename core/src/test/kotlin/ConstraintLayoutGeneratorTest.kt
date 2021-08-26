import assertions.assertThatAnyFunctionEquals
import org.junit.jupiter.api.Test
import readers.XmlReaderImpl

class ConstraintLayoutGeneratorTest {

    private val xmlReader = XmlReaderImpl()

    @Test
    fun `given no attributes are defined, function should be ConstraintLayout () {}`() {
        val composeGenerator = xmlReader.read(
            content = """ <ConstraintLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |androidx.constraintlayout.compose.ConstraintLayout () {
            |}
            |
            """.trimMargin()
        )
    }

    @Test
    fun `given button is top_top parent, function should contain top linkTo parentTop`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <Button
                        android:id="@+id/btn"
                        app:layout_constraintTop_toTopOf="parent"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |androidx.compose.foundation.layout.ConstraintLayout () {
            |  val btnRef = ConstrainedLayoutReference(Any())
            |  androidx.compose.material.Button(onClick = {}, modifier = Modifier.constrainAs(btnRef, {
            |     top.linkTo(parent.top)
            |  })) {
            |  }
            |}
            |
        """.trimIndent().trimMargin()
        )
    }

}