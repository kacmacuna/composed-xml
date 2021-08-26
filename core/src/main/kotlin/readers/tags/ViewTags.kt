package readers.tags

import generators.nodes.ViewNode
import org.w3c.dom.Element
import readers.elements.*

enum class ViewTags(val value: String) {
    TEXT_VIEW("TextView"),
    LINEAR_LAYOUT("LinearLayout"),
    BUTTON("Button"),
    FRAME_LAYOUT("FrameLayout"),
    CONSTRAINT_LAYOUT("androidx.constraintlayout.widget.ConstraintLayout"),
    EDIT_TEXT("EditText"),;

    fun toLayoutElement(
        element: Element,
    ): LayoutElement<ViewNode> {
        return when (this) {
            TEXT_VIEW -> TextViewElement(element)
            LINEAR_LAYOUT -> LinearLayoutElement(element)
            BUTTON -> ButtonElement(element)
            FRAME_LAYOUT -> FrameLayoutElement(element)
            EDIT_TEXT -> EditTextElement(element)
            CONSTRAINT_LAYOUT -> ConstraintLayoutElement(element)
        }
    }

    companion object {
        fun fromString(value: String): ViewTags {
            return values().find { it.value == value } ?: error("Unhandled view type: $value")
        }
    }

}