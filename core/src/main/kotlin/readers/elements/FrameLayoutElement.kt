package readers.elements

import generators.nodes.FrameLayoutNode
import generators.nodes.ViewNode
import org.w3c.dom.Element

class FrameLayoutElement(
    element: Element,
    private val parentNode: ViewNode?
) : LayoutElement<FrameLayoutNode>(element) {

    override fun node(): FrameLayoutNode {
        return FrameLayoutNode(
            children = Iterable { ViewGroupIterator(this) },
            info = FrameLayoutNode.Info(
                id = getViewIdNameTag(),
                alignment = getAlignmentFromGravity()
            ),
            _parent = parentNode
        )
    }

    private fun getAlignmentFromGravity(): FrameLayoutNode.Alignment {
        return when(getAttribute("android:gravity")){
            "top|start", "start|top" -> FrameLayoutNode.Alignment.TopStart
            "center" -> FrameLayoutNode.Alignment.Center
            "bottom|center", "center|bottom" -> FrameLayoutNode.Alignment.BottomCenter
            "center|end", "end|center" -> FrameLayoutNode.Alignment.CenterEnd
            "" -> FrameLayoutNode.Alignment.NoAlignment
            else -> TODO("Unhandled gravity types")
        }
    }

}