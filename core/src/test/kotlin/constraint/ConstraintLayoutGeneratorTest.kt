package constraint

import assertions.assertThatAnyFunctionEquals
import data.XmlReaderTest
import org.junit.jupiter.api.Test

class ConstraintLayoutGeneratorTest {

    private val xmlReader = XmlReaderTest()

    @Test
    fun `given no attributes are defined, function should be ConstraintLayout () {}`() {
        val composeGenerator = xmlReader.read(
            content = """ <androidx.constraintlayout.widget.ConstraintLayout android:id="@+id/content" /> """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout () {
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
            |ConstraintLayout () {
            |  val btnRef = ConstrainedLayoutReference(Any())
            |  Button(onClick = {}, modifier = Modifier.constrainAs(btnRef, {
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
            |ConstraintLayout () {
            |  val txtRef = ConstrainedLayoutReference(Any())
            |  Text("", modifier = Modifier.constrainAs(txtRef, {
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
            |ConstraintLayout () {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  val btnRef = ConstrainedLayoutReference(Any())
            |  Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start)
            |  }
            |  ))
            |  Button(onClick = {}, modifier = Modifier.constrainAs(btnRef, {
            |    start.linkTo(textRef.end)
            |  }
            |  )) {
            |  }
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given text is start_start parent with marginStart 20,dp, function should be text start linkTo parent start 20,dp`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginStart="20dp"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout () {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start, 20.dp)
            |  }
            |  ))
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given text is start_start parent with marginHorizontal 20,dp, function should be text start linkTo parent start 20,dp`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginHorizontal="20dp"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout () {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start, 20.dp)
            |  }
            |  ))
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

}