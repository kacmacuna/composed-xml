package readers.tags

import generators.nodes.ViewNode
import org.w3c.dom.Element
import readers.elements.*
import readers.imports.Imports

enum class ViewTags(val value: String) {
    TEXT_VIEW("TextView"),
    LINEAR_LAYOUT("LinearLayout"),
    BUTTON("Button"),
    FRAME_LAYOUT("FrameLayout"),
    CONSTRAINT_LAYOUT("androidx.constraintlayout.widget.ConstraintLayout"),
    EDIT_TEXT("EditText"),
    SCROLL_VIEW("ScrollView"),
    HORIZONTAL_SCROLL_VIEW("HorizontalScrollView");

    fun toLayoutElement(
        element: Element,
        imports: Imports
    ): LayoutElement<ViewNode> {
        return when (this) {
            TEXT_VIEW -> TextViewElement(element, imports)
            LINEAR_LAYOUT -> LinearLayoutElement(element, imports)
            BUTTON -> ButtonElement(element, imports)
            FRAME_LAYOUT -> FrameLayoutElement(element, imports)
            EDIT_TEXT -> EditTextElement(element, imports)
            CONSTRAINT_LAYOUT -> ConstraintLayoutElement(element, imports)
            SCROLL_VIEW, HORIZONTAL_SCROLL_VIEW -> ScrollViewElement(element, imports, this == HORIZONTAL_SCROLL_VIEW)
        }
    }

    companion object {
        fun fromString(value: String): ViewTags {
            return values().find { it.value == value } ?: error("Unhandled view type: $value")
        }
    }

}