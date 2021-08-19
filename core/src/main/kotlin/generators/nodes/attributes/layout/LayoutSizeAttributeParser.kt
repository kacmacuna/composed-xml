package generators.nodes.attributes.layout

import java.lang.IllegalStateException

class LayoutSizeAttributeParser {

    fun parseW(attribute: String): LayoutWidth {
        return when {
            attribute == "match_parent" -> LayoutSizeMatchParent.Width()
            attribute == "wrap_content" -> LayoutSizeWrapContent.Width()
            attribute.contains("dp") -> LayoutSizeInDp.Width(attribute.removeSuffix("dp").toInt())
            attribute.isEmpty() -> EmptyLayoutSize
            else -> throw IllegalStateException("Unhandled layout_width: $attribute")
        }
    }

    fun parseH(attribute: String): LayoutHeight {
        return when {
            attribute == "match_parent" -> LayoutSizeMatchParent.Height()
            attribute == "wrap_content" -> LayoutSizeWrapContent.Height()
            attribute.contains("dp") -> LayoutSizeInDp.Height(attribute.removeSuffix("dp").toInt())
            attribute.isEmpty() -> EmptyLayoutSize
            else -> throw IllegalStateException("Unhandled layout_width: $attribute")
        }
    }

}