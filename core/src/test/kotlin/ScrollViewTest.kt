import assertions.assertThatAnyFunctionEquals
import data.XmlReaderTest
import org.junit.jupiter.api.Test

class ScrollViewTest {

    val xmlReader = XmlReaderTest()

    @Test
    fun `given ScrollView's child  is ConstraintLayout, function should be ConstraintLayout(Modifier,verticalScroll)`() {
        val generator = xmlReader.read(
            """
                <ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
                    xmlns:app="http://schemas.android.com/apk/res-auto"
                    android:layout_width="match_parent"
                    android:layout_height="200dp">

                    <androidx.constraintlayout.widget.ConstraintLayout
                        android:layout_width="match_parent"
                        android:layout_height="0dp">

                        <Button
                            android:id="@+id/btn1"
                            android:layout_width="match_parent"
                            android:layout_height="150dp"
                            android:text="Hello"
                            app:layout_constraintTop_toTopOf="parent" />

                        <Button
                            android:id="@+id/btn2"
                            android:layout_width="match_parent"
                            android:layout_height="150dp"
                            android:text="Hello"
                            app:layout_constraintTop_toBottomOf="@id/btn1" />
                    </androidx.constraintlayout.widget.ConstraintLayout>
                </ScrollView>
            """.trimIndent(),
            "temp"
        )

        generator.generate() assertThatAnyFunctionEquals """
            |ConstraintLayout (modifier = Modifier.fillMaxWidth().height(200.dp).verticalScroll(rememberScrollState())) {
            |  val btn1Ref = ConstrainedLayoutReference(Any())
            |  val btn2Ref = ConstrainedLayoutReference(Any())
            |  Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(150.dp).constrainAs(btn1Ref, {
            |    top.linkTo(parent.top)
            |  }
            |  )) {
            |    Text("Hello")
            |  }
            |  Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(150.dp).constrainAs(btn2Ref, {
            |    top.linkTo(btn1Ref.bottom)
            |  }
            |  )) {
            |    Text("Hello")
            |  }
            |}
            |
        """.trimIndent().trimMargin()

    }

    @Test
    fun `given H ScrollView's child  is FrameLayout, function should be Box(Modifier,horizontalScroll)`() {
        val generator = xmlReader.read(
            """
                <HorizontalScrollView xmlns:android="http://schemas.android.com/apk/res/android"
                    xmlns:app="http://schemas.android.com/apk/res-auto"
                    android:layout_width="match_parent"
                    android:layout_height="200dp">

                    <FrameLayout
                        android:layout_width="match_parent"
                        android:layout_height="0dp">

                        <EditText
                            android:id="@+id/btn1"
                            android:layout_width="match_parent"
                            android:layout_height="150dp"
                            android:text="Hello" />

                        <TextView
                            android:id="@+id/btn2"
                            android:layout_width="match_parent"
                            android:layout_height="150dp" />
                    </FrameLayout>
                </HorizontalScrollView>
            """.trimIndent(),
            "temp"
        )

        generator.generate() assertThatAnyFunctionEquals """
            |Box (modifier = Modifier.fillMaxWidth().height(200.dp).horizontalScroll(rememberScrollState())) {
            |  Text("", modifier = Modifier.fillMaxWidth().height(150.dp))
            |  TextField(value = "Hello", modifier = Modifier.fillMaxWidth().height(150.dp), onValueChange = {})
            |}
            |
        """.trimIndent().trimMargin()

    }

    @Test
    fun `given H ScrollView's child  is Linearlayout, function should be Row(Modifier,horizontalScroll)`() {
        val generator = xmlReader.read(
            """
                <HorizontalScrollView xmlns:android="http://schemas.android.com/apk/res/android"
                    xmlns:app="http://schemas.android.com/apk/res-auto"
                    android:layout_width="match_parent"
                    android:layout_height="200dp">

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="0dp">

                        <EditText
                            android:id="@+id/btn1"
                            android:layout_width="match_parent"
                            android:layout_height="150dp"
                            android:text="Hello" />

                        <TextView
                            android:id="@+id/btn2"
                            android:layout_width="match_parent"
                            android:layout_height="150dp" />
                    </LinearLayout>
                </HorizontalScrollView>
            """.trimIndent(),
            "temp"
        )

        generator.generate() assertThatAnyFunctionEquals """
            |Row (modifier = Modifier.fillMaxWidth().height(200.dp).horizontalScroll(rememberScrollState())) {
            |  Text("", modifier = Modifier.fillMaxWidth().height(150.dp))
            |  TextField(value = "Hello", modifier = Modifier.fillMaxWidth().height(150.dp), onValueChange = {})
            |}
            |
        """.trimIndent().trimMargin()

    }

}