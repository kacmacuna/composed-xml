package readers.tags

import generators.nodes.ParentViewNode
import generators.nodes.ViewNode
import org.w3c.dom.Element
import readers.elements.*

enum class ViewTags(val value: String) {
    TEXT_VIEW("TextView"),
    LINEAR_LAYOUT("LinearLayout"),
    BUTTON("Button"),
    FRAME_LAYOUT("FrameLayout");

    fun toLayoutElement(
        element: Element,
        parentNode: ViewNode?
    ): LayoutElement<ViewNode> {
        return when (this) {
            TEXT_VIEW -> TextViewElement(element, parentNode)
            LINEAR_LAYOUT -> LinearLayoutElement(element, parentNode)
            BUTTON -> ButtonElement(element, parentNode)
            FRAME_LAYOUT -> FrameLayoutElement(element, parentNode)
        }
    }

    companion object {
        fun fromString(value: String): ViewTags {
            return values().find { it.value == value } ?: error("Unhandled view type: $value")
        }
    }

}