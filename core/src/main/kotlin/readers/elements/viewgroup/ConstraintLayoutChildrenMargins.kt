package readers.elements.viewgroup

import generators.nodes.ViewNode
import generators.nodes.attributes.constraints.ConstraintLayoutMargin
import generators.nodes.attributes.constraints.ConstraintsParser
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import poet.chained.ChainedMemberName
import readers.elements.LayoutElement
import readers.imports.Imports
import readers.tags.ViewTags

class ConstraintLayoutChildrenMargins(
    private val layoutElement: LayoutElement<ViewNode>
) : Iterable<ConstraintLayoutMargin> {

    private val constraintsParser = ConstraintsParser()


    override fun iterator(): Iterator<ConstraintLayoutMargin> {
        return ViewTags.values().map {
            layoutElement.getElementsByTagName(it.value)
        }.map { elements(it) }.flatMap { list ->
            list.map { element ->
                constraintsParser.parse(element).details
            }
        }.map { details ->
            details.map { it.margin }
        }.flatten().iterator()
    }

    private fun elements(nodeList: NodeList): MutableList<Element> {
        val totalNodeList = mutableListOf<Element>()
        for (i in 0 until nodeList.length) {
            val element = nodeList.item(i) as Element
            if (element.parentNode == layoutElement.originalElement)
                totalNodeList.add(element)
        }
        return totalNodeList
    }
}