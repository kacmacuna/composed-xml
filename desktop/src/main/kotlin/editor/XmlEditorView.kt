package editor

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import readers.tags.ViewTags
import theme.AppTheme

fun xmlCode(str: String) = buildAnnotatedString {
    withStyle(AppTheme.xmlCode.simple) {
        val strFormatted = str.replace("\t", "  ")
        append(strFormatted)
        addStyle(AppTheme.xmlCode.viewType, strFormatted, "/>")
        addStyle(AppTheme.xmlCode.viewType, strFormatted, ">")
        ViewTags.values().map { it.value }.forEach {
            addStyle(AppTheme.xmlCode.viewType, strFormatted, "<$it")
            addStyle(AppTheme.xmlCode.viewType, strFormatted, "</$it>")
        }
        addStyle(AppTheme.xmlCode.viewType, strFormatted, ">")
        addStyle(AppTheme.xmlCode.prefix, strFormatted, "android:")
        addStyle(AppTheme.xmlCode.prefix, strFormatted, "app:")
        addStyle(AppTheme.xmlCode.prefix, strFormatted, "tools:")
        addStyle(AppTheme.xmlCode.attributeValue, strFormatted, Regex("([\"'])(?:(?=(\\\\?))\\2.)*?\\1"))
    }
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: String) {
    addStyle(style, text, Regex.fromLiteral(regexp))
}

private fun AnnotatedString.Builder.addStyle(style: SpanStyle, text: String, regexp: Regex) {
    for (result in regexp.findAll(text)) {
        addStyle(style, result.range.first, result.range.last + 1)
    }
}
