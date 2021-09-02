package editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import codeString
import readers.XmlReaderImpl
import theme.AppTheme

@Composable
fun EditorHeader() {
    Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
        Text("Xml Editor", modifier = Modifier.weight(1F), style = MaterialTheme.typography.h5)
        Text("Composable Review", modifier = Modifier.weight(1F), style = MaterialTheme.typography.h5)
    }
}

@Composable
fun EditorBody(xmlReader: XmlReaderImpl, initialXmlBody: String) {
    Row(Modifier.wrapContentWidth(), Arrangement.spacedBy(5.dp)) {
        val xmlValue = remember { mutableStateOf(TextFieldValue(xmlCode(initialXmlBody))) }
        val composableValue = remember { mutableStateOf("") }
        XmlEditor(xmlValue, xmlReader, composableValue)
        Spacer(Modifier.width(1.dp).fillMaxHeight())
        Box(modifier = Modifier.weight(1F).background(AppTheme.colors.backgroundDark).fillMaxHeight().fillMaxWidth()){
            SelectionContainer(
                Modifier.verticalScroll(
                    rememberScrollState()
                )
            ) {
                Text(
                    text = codeString(composableValue.value),
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                )
            }
        }

    }
}

@Composable
private fun RowScope.XmlEditor(
    xmlValue: MutableState<TextFieldValue>,
    xmlReader: XmlReaderImpl,
    composableValue: MutableState<String>
) {
    if (xmlValue.value.text.isNotEmpty()) onXmlValueChange(xmlValue, xmlValue.value, xmlReader, composableValue)
    TextField(
        value = xmlValue.value,
        modifier = Modifier.weight(1F).background(AppTheme.xmlCode.background).fillMaxHeight(),
        textStyle = MaterialTheme.typography.body1,
        onValueChange = {
            onXmlValueChange(xmlValue, it, xmlReader, composableValue)
        },
    )
}

private fun onXmlValueChange(
    xmlValue: MutableState<TextFieldValue>,
    it: TextFieldValue,
    xmlReader: XmlReaderImpl,
    composableValue: MutableState<String>
) {
    xmlValue.value = TextFieldValue(xmlCode(it.text), it.selection, it.composition)
    val generator = xmlReader.read(it.text, "temp")
    val stringBuilder = StringBuilder()
    generator.generate().writeTo(stringBuilder)
    composableValue.value = if (xmlValue.value.text.isEmpty()) ""
    else if (stringBuilder.toString().isNotEmpty())
        stringBuilder.toString().replace("\t", "   ")
    else
        composableValue.value.replace("\t", "   ")
}