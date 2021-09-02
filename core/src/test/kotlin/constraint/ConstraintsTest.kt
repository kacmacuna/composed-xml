package constraint

import data.createFakeServiceLocator
import generators.nodes.ViewNode
import generators.nodes.attributes.constraints.ConstraintDetails
import generators.nodes.attributes.constraints.ConstraintDirection
import generators.nodes.attributes.constraints.Constraints
import generators.nodes.attributes.constraints.ConstraintsParser
import generators.nodes.attributes.size.DPAttribute
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import readers.elements.LayoutElement
import readers.imports.Imports

class ConstraintsTest {

    private val constraintsParser = ConstraintsParser()

    @BeforeEach
    fun setUp() {
        createFakeServiceLocator()
    }

    @Test
    fun `given view does not have id, unnamedRef should be created`() {
        val actual = constraintsParser.parse(mockk<LayoutElement<ViewNode>> {
            every { tagName } returns "TextView"
            every { getAttribute("android:id") } returns ""
            every { hasAttribute("app:layout_constraintTop_toTopOf") } returns true
            every { getAttribute("android:layout_marginTop") } returns "10dp"
            every { getAttribute("app:layout_constraintTop_toTopOf") } returns "parent"
            every { hasAttribute(not("app:layout_constraintTop_toTopOf")) } returns false
        })

        Assertions.assertEquals(
            """
                ConstrainedLayoutReference(Any()), {
                  top.linkTo(parent.top, 10.dp)
                }
                
            """.trimIndent(),
            actual.argument().toString()
        )
    }

}