package constraint

import data.FakeImports
import data.nodes.FakeConstraintLayoutInfo
import data.nodes.FakeViewNode
import generators.nodes.ConstraintLayoutNode
import generators.nodes.attributes.constraints.Constraints
import generators.nodes.attributes.layout.LayoutSizeInDp
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import poet.chained.ChainedMemberName

class ConstraintNodeTest {

    @BeforeEach
    fun setUp() {
        ServiceLocator.createInstance(FakeImports)
    }

    @Test
    fun `given width and height 0dp generated width and height should be fillToConstraints`() {
        val constraints = Constraints(
            constraintId = "fakeId",
            details = emptyList(),
            isWidthFillToConstraints = true,
            isHeightFillToConstraints = true
        )

        val node = ConstraintLayoutNode(
            _children = listOf(
                FakeViewNode(
                    FakeViewNode.FakeInfo(
                        width = LayoutSizeInDp.Width(0),
                        chainedMemberNames = listOf(
                            ChainedMemberName(
                                constraints.memberNamePrefix,
                                constraints.argument()
                            )
                        )
                    ),
                )
            ),
            info = FakeConstraintLayoutInfo(),
            imports = FakeImports
        )

        assertThat(
            node.body().toString(),
            Matchers.allOf(
                Matchers.containsString("width = Dimension.fillToConstraints"),
                Matchers.containsString("height = Dimension.fillToConstraints"),
            )
        )
    }

}