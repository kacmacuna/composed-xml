@file:Suppress("FunctionName")

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import editor.xmlCode
import readers.XmlReaderImpl
import theme.AppTheme

fun main() = application {
    val xmlReader = XmlReaderImpl()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Xml To Composable",
        state = rememberWindowState(width = 1200.dp, height = 800.dp)
    ) {
        MaterialTheme {
            Column {
                EditorHeader()
                EditorBody(xmlReader)
            }
        }
    }
}

@Composable
private fun EditorHeader() {
    Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
        Text("Xml Editor", modifier = Modifier.weight(1F), style = MaterialTheme.typography.h5)
        Text("Composable Review", modifier = Modifier.weight(1F), style = MaterialTheme.typography.h5)
    }
}

@Composable
private fun EditorBody(xmlReader: XmlReaderImpl) {
    Row(Modifier.wrapContentWidth().fillMaxHeight(), Arrangement.spacedBy(5.dp)) {
        val xmlValue = remember { mutableStateOf(TextFieldValue("")) }
        val composableValue = remember { mutableStateOf("") }
        XmlEditor(xmlValue, xmlReader, composableValue)
        Spacer(Modifier.width(1.dp).fillMaxHeight())
        Text(
            text = codeString(composableValue.value),
            modifier = Modifier.weight(1F).background(AppTheme.colors.backgroundDark).fillMaxHeight(),
        )
    }
}

@Composable
private fun RowScope.XmlEditor(
    xmlValue: MutableState<TextFieldValue>,
    xmlReader: XmlReaderImpl,
    composableValue: MutableState<String>
) {

    TextField(
        value = xmlValue.value,
        modifier = Modifier.weight(1F).background(AppTheme.xmlCode.background).fillMaxHeight(),
        textStyle = MaterialTheme.typography.body1,
        onValueChange = {
            xmlValue.value = TextFieldValue(xmlCode(it.text), it.selection, it.composition)
            val generator = xmlReader.read(it.text, "temp")
            val stringBuilder = StringBuilder()
            generator.generate().writeTo(stringBuilder)
            composableValue.value = if (xmlValue.value.text.isEmpty()) ""
            else if (stringBuilder.toString().isNotEmpty())
                stringBuilder.toString().replace("\t", "   ")
            else
                composableValue.value.replace("\t", "   ")
        },
    )
}