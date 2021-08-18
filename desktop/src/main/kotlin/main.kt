import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import readers.XmlReaderImpl

fun main() = application {
    val xmlReader = XmlReaderImpl()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Xml To Composable",
        state = rememberWindowState(width = 1200.dp, height = 800.dp)
    ) {
        MaterialTheme {
            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                val xmlValue = remember { mutableStateOf("") }
                val composableValue = remember { mutableStateOf("") }
                TextField(
                    value = xmlValue.value,
                    modifier = Modifier.weight(1F).background(Color.Gray),
                    onValueChange = {
                        xmlValue.value = it
                        val generator = xmlReader.read(it, "temp")
                        val stringBuilder = StringBuilder()
                        generator.generate().writeTo(stringBuilder)
                        composableValue.value = if (xmlValue.value.isEmpty()) ""
                        else if (stringBuilder.toString().isNotEmpty())
                            stringBuilder.toString()
                        else
                            composableValue.value
                    },
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = composableValue.value,
                    modifier = Modifier.weight(1F).background(Color.Cyan),
                )
            }
        }
    }
}