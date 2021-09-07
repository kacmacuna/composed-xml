@file:Suppress("TestFunctionName")

package data.nodes

import generators.nodes.ConstraintLayoutNode
import generators.nodes.ViewId
import generators.nodes.attributes.colors.ColorAttributeEmpty
import generators.nodes.attributes.layout.EmptyLayoutSize

fun FakeConstraintLayoutInfo() = ConstraintLayoutNode.Info(
    backgroundColor = ColorAttributeEmpty,
    id = ViewId("content"),
    width = EmptyLayoutSize,
    height = EmptyLayoutSize,
    chainedMemberNames = emptyList(),
    margins = emptyList()
)