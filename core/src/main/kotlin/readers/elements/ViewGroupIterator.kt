package readers.elements

import generators.nodes.ViewNode
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import readers.tags.ViewTags

class ViewGroupIterator(
    private val layoutElement: LayoutElement<ViewNode>
) : Iterator<ViewNode> {
    override fun hasNext(): Boolean {
        return currentIndex < totalNodeList.size
    }

    private var currentIndex = 0

    private val totalNodeList: MutableList<Element> = mutableListOf()

    init {
        ViewTags.values().map {
            layoutElement.getElementsByTagName(it.value)
        }.forEach { writeInList(it) }
    }

    override fun next(): ViewNode {
        val element = totalNodeList[currentIndex]
        currentIndex++

        return ViewTags.fromString(element.nodeName).toLayoutElement(element).node()
    }

    private fun writeInList(nodeList: NodeList) {
        for (i in 0 until nodeList.length) {
            val element = nodeList.item(i) as Element
            if (element.parentNode == layoutElement.originalElement)
                totalNodeList.add(element)
        }
    }
}