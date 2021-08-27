import assertions.assertThatAnyFunctionEquals
import org.junit.jupiter.api.Test
import readers.XmlReaderImpl

class ConstraintLayoutGeneratorTest {

    private val xmlReader = XmlReaderImpl()

    @Test
    fun `given no attributes are defined, function should be ConstraintLayout () {}`() {
        val composeGenerator = xmlReader.read(
            content = """ <androidx.constraintlayout.widget.ConstraintLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |androidx.compose.foundation.layout.ConstraintLayout () {
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
                        app:layout_constraintTop_toTopOf="parent"/>
                                    
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
            |    top.linkTo(parent.top)
            |  }
            |  )) {
            |  }
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given text is end_end parent and bottom_bottom parent, function should be bottom, end linkTo parent bottom, end`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/txt"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |androidx.compose.foundation.layout.ConstraintLayout () {
            |  val txtRef = ConstrainedLayoutReference(Any())
            |  androidx.compose.material.Text("", modifier = Modifier.constrainAs(txtRef, {
            |    bottom.linkTo(parent.bottom)
            |    end.linkTo(parent.end)
            |  }
            |  ))
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given text is start_start parent and btn start_end text, function should be text start linkTo parent start and btn start linkTo text end`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        app:layout_constraintStart_toStartOf="parent"/>
                        
                    <Button
                        android:id="@+id/btn"
                        app:layout_constraintStart_toEndOf="@id/text"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |androidx.compose.foundation.layout.ConstraintLayout () {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  val btnRef = ConstrainedLayoutReference(Any())
            |  androidx.compose.material.Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start)
            |  }
            |  ))
            |  androidx.compose.material.Button(onClick = {}, modifier = Modifier.constrainAs(btnRef, {
            |    start.linkTo(textRef.end)
            |  }
            |  )) {
            |  }
            |}
            |
            """.trimIndent().trimMargin()
        )
    }
}