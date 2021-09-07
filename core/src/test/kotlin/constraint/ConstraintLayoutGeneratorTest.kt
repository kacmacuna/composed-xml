package constraint

import assertions.assertThatAnyFunctionEquals
import data.XmlData
import data.XmlReaderTest
import org.junit.jupiter.api.Test

class ConstraintLayoutGeneratorTest {

    private val xmlReader = XmlReaderTest()

    @Test
    fun `given no attributes are defined, function should be ConstraintLayout () {}`() {
        val composeGenerator = xmlReader.read(
            content = XmlData.ConstraintLayout.EMPTY_BODY,
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
            |}
            |
            """.trimMargin()
        )
    }

    @Test
    fun `given button is top_top parent, function should contain top linkTo parentTop`() {
        val composeGenerator = xmlReader.read(
            content = XmlData.ConstraintLayout.BUTTON_TOP_TOP_PARENT,
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
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
            content = XmlData.ConstraintLayout.TEXT_END_END_PARENT_AND_BOTTOM_BOTTOM_PARENT,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
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
            content = XmlData.ConstraintLayout.TEXT_START_START_PARENT_AND_BTN_START_END_TEXT,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
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
            content = XmlData.ConstraintLayout.TEXT_START_START_PARENT_WITH_MARGIN_20_DP,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
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
    fun `given text is start_start parent with marginStart res dp_20, function should be text start linkTo parent start dp20`() {
        val composeGenerator = xmlReader.read(
            content = XmlData.ConstraintLayout.TEXT_START_START_PARENT_WITH_MARGIN_RES_20_DP,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  val dp20 = dimensionResource(id = R.dimen.dp_20)
            |  Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start, dp20)
            |  }
            |  ))
            |}
            |
            """.trimIndent().trimMargin()
        )
    }

    @Test
    fun `given marginStart and marginEnd are res dp_20 only one variable should be created`() {
        val composeGenerator = xmlReader.read(
            content = XmlData.ConstraintLayout.TEXT_WITH_SEVERAL_SAME_MARGINS,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
            |  val textRef = ConstrainedLayoutReference(Any())
            |  val dp20 = dimensionResource(id = R.dimen.dp_20)
            |  Text("", modifier = Modifier.constrainAs(textRef, {
            |    start.linkTo(parent.start, dp20)
            |    end.linkTo(parent.end, dp20)
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
            content = XmlData.ConstraintLayout.TEXT_WITH_MARGIN_HORIZONTAL,
            fileName = "test"
        )

        val file = composeGenerator.generate()


        file.assertThatAnyFunctionEquals(
            """
            |ConstraintLayout() {
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