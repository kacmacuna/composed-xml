package readers.elements

import generators.nodes.ConstraintLayoutNode
import org.w3c.dom.Element
import readers.elements.viewgroup.ConstraintLayoutChildrenMargins
import readers.elements.viewgroup.ConstraintLayoutIterator
import readers.imports.Imports

class ConstraintLayoutElement(
    element: Element,
    private val imports: Imports
) : LayoutElement<ConstraintLayoutNode>(element) {
    override fun node(): ConstraintLayoutNode {
        val constraintLayoutChildrenMargins = ConstraintLayoutChildrenMargins(this)
        return ConstraintLayoutNode(
            children = Iterable { ConstraintLayoutIterator(this, imports) },
            info = ConstraintLayoutNode.Info(
                id = getViewIdNameTag(),
                backgroundColor = colorAttributeParser.parse(getAttribute("android:background")),
                width = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
                height = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
                margins = constraintLayoutChildrenMargins
            ),
            imports = imports
        )
    }

}
