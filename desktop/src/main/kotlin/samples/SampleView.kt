package samples

import MainBody
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import editor.xmlCode
import theme.AppTheme

@Composable
fun SamplesListView(
    modifier: Modifier = Modifier,
    annotatedStrings: List<SampleItem>,
    mainBody: MutableState<MainBody>
) {
    LazyColumn(modifier = modifier.background(AppTheme.colors.backgroundDark)) {
        items(annotatedStrings.size) { index ->
            TextButton(
                onClick = { mainBody.value = MainBody(BottomNavigationViewItem.Editor, annotatedStrings[index].body) },
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    text = xmlCode(annotatedStrings[index].header),
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}