package readers.tags

import generators.nodes.ViewNode
import org.w3c.dom.Element
import poet.chained.ChainedMemberName
import readers.elements.*
import readers.imports.Imports
import javax.swing.text.html.ImageView

enum class ViewTags(val value: String) {
    TEXT_VIEW("TextView"),
    LINEAR_LAYOUT("LinearLayout"),
    BUTTON("Button"),
    FRAME_LAYOUT("FrameLayout"),
    CONSTRAINT_LAYOUT("androidx.constraintlayout.widget.ConstraintLayout"),
    EDIT_TEXT("EditText"),
    SCROLL_VIEW("ScrollView"),
    HORIZONTAL_SCROLL_VIEW("HorizontalScrollView"),
    IMAGE_VIEW("ImageView");

    fun toLayoutElement(
        element: Element,
        imports: Imports,
        chainedMemberNames: List<ChainedMemberName> = listOf()
    ): LayoutElement<ViewNode> {
        return when (this) {
            TEXT_VIEW -> TextViewElement(element, imports, chainedMemberNames)
            LINEAR_LAYOUT -> LinearLayoutElement(element, imports, chainedMemberNames)
            BUTTON -> ButtonElement(element, imports, chainedMemberNames)
            FRAME_LAYOUT -> FrameLayoutElement(element, imports, chainedMemberNames)
            EDIT_TEXT -> EditTextElement(element, imports, chainedMemberNames)
            CONSTRAINT_LAYOUT -> ConstraintLayoutElement(element, imports)
            SCROLL_VIEW, HORIZONTAL_SCROLL_VIEW -> ScrollViewElement(element, imports, this == HORIZONTAL_SCROLL_VIEW)
            IMAGE_VIEW -> ImageViewElement(element, imports, chainedMemberNames)
        }
    }

    companion object {
        fun fromString(value: String): ViewTags {
            return values().find { it.value == value } ?: error("Unhandled view type: $value")
        }
    }

}