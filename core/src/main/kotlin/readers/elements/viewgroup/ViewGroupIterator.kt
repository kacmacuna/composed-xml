package readers.elements.viewgroup

import generators.nodes.ViewNode
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import readers.elements.LayoutElement
import readers.imports.Imports
import readers.tags.ViewTags

open class ViewGroupIterator(
    private val layoutElement: LayoutElement<ViewNode>,
    private val imports: Imports
) : Iterator<ViewNode> {
    override fun hasNext(): Boolean {
        return currentIndex < totalNodeList.size
    }

    protected var currentIndex = 0

    protected val totalNodeList: MutableList<Element> = mutableListOf()

    init {
        ViewTags.values().map {
            layoutElement.getElementsByTagName(it.value)
        }.forEach { writeInList(it) }
    }

    override fun next(): ViewNode {
        val element = totalNodeList[currentIndex]
        currentIndex++

        return ViewTags.fromString(element.nodeName).toLayoutElement(element, imports).node()
    }

    private fun writeInList(nodeList: NodeList) {
        for (i in 0 until nodeList.length) {
            val element = nodeList.item(i) as Element
            if (element.parentNode == layoutElement.originalElement)
                totalNodeList.add(element)
        }
    }
}