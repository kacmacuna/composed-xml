package readers.elements.viewgroup

import generators.nodes.ViewNode
import generators.nodes.attributes.constraints.ConstraintsParser
import poet.chained.ChainedMemberName
import readers.elements.LayoutElement
import readers.imports.Imports
import readers.tags.ViewTags

class ConstraintLayoutIterator(
    private val layoutElement: LayoutElement<ViewNode>,
    private val imports: Imports
) : ViewGroupIterator(layoutElement, imports) {
    private val constraintsParser = ConstraintsParser()
    override fun next(): ViewNode {
        val element = totalNodeList[currentIndex]
        val constraints = constraintsParser.parse(element)
        currentIndex++

        return ViewTags.fromString(element.nodeName).toLayoutElement(
            element,
            imports,
            listOf(
                ChainedMemberName(
                    constraints.memberNamePrefix,
                    constraints.argument()
                )
            )
        ).node()
    }
}