package readers.elements

import com.squareup.kotlinpoet.CodeBlock
import generators.nodes.ViewNode
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.imports.Imports

class ScrollViewElement(
    element: Element,
    private val imports: Imports,
    private val isHorizontal: Boolean
) : LayoutElement<ViewNode>(element) {
    override fun node(): ViewNode {
        val childViewNode = Iterable { ViewGroupIterator(this, imports) }.first()
        return childViewNode.copyWithInfo(
            layoutWidth = layoutSizeAttributeParser.parseW(getAttribute("android:layout_width")),
            layoutHeight = layoutSizeAttributeParser.parseH(getAttribute("android:layout_height")),
            chainedMemberNames = arrayOf(
                ChainedMemberName(
                    prefix = if (isHorizontal)
                        imports.attributeImports.horizontalScroll
                    else
                        imports.attributeImports.verticalScroll,
                    argument = CodeBlock.of("%M()", imports.attributeImports.rememberScrollState),
                )
            )
        )
    }
}