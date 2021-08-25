package theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle

object AppTheme {
    val colors: Colors = Colors()

    val code: Code = Code()

    val xmlCode: XmlCode = XmlCode()

    class Colors(
        val backgroundDark: Color = Color(0xFF2B2B2B),
        val backgroundMedium: Color = Color(0xFF3C3F41),
        val backgroundLight: Color = Color(0xFF4E5254),

        val material: androidx.compose.material.Colors = darkColors(
            background = backgroundDark,
            surface = backgroundMedium,
            primary = Color.White
        ),
    )

    class XmlCode(
        val viewType: SpanStyle = SpanStyle(Color(0xFFE7BE6A)),
        val prefix: SpanStyle = SpanStyle(Color(0xFF7268A5)),
        val attributeValue: SpanStyle = SpanStyle(Color(0xFF628658)),
        val simple: SpanStyle = SpanStyle(Color(0xFFBABABA)),
        val background: Color = Color(0xFF2B2B2B)
    )

    class Code(
        val simple: SpanStyle = SpanStyle(Color(0xFFA9B7C6)),
        val value: SpanStyle = SpanStyle(Color(0xFF6897BB)),
        val keyword: SpanStyle = SpanStyle(Color(0xFFCC7832)),
        val punctuation: SpanStyle = SpanStyle(Color(0xFFA1C17E)),
        val annotation: SpanStyle = SpanStyle(Color(0xFFBBB529)),
        val comment: SpanStyle = SpanStyle(Color(0xFF808080))
    )
}