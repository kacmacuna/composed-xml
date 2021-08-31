@file:Suppress("FunctionName")

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import editor.EditorBody
import editor.EditorHeader
import readers.XmlReaderImpl
import samples.SampleItem
import samples.SamplesListView

fun main() = application {
    val xmlReader = XmlReaderImpl()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Xml To Composable",
        state = rememberWindowState(width = 1200.dp, height = 800.dp)
    ) {
        val mainBody = remember { mutableStateOf(MainBody(BottomNavigationViewItem.Editor)) }
        MaterialTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                when (mainBody.value.bottomNavigationViewItem) {
                    BottomNavigationViewItem.Samples -> {
                        SamplesListView(
                            modifier = Modifier.weight(1F),
                            SampleItem.DATA,
                            mainBody
                        )
                    }
                    BottomNavigationViewItem.Editor -> {
                        Column(modifier = Modifier.weight(1F)) {
                            EditorHeader()
                            SelectionContainer { EditorBody(xmlReader, mainBody.value.initialXmlBody) }
                        }
                    }
                }
                BottomNavigationView(mainBody)
            }
        }
    }
}

data class MainBody(
    val bottomNavigationViewItem: BottomNavigationViewItem,
    val initialXmlBody: String = ""
)