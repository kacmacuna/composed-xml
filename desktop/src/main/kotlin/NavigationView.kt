@file:Suppress("FunctionName")

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationView(mainBody: MutableState<MainBody>) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(contentDescription = "Editor", imageVector = Icons.Outlined.Home) },
            label = { Text(text = "Editor") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White.copy(0.4f),
            alwaysShowLabel = true,
            selected = mainBody.value.bottomNavigationViewItem == BottomNavigationViewItem.Editor,
            onClick = {
                mainBody.value = MainBody(BottomNavigationViewItem.Editor)
            }
        )

        BottomNavigationItem(
            icon = { Icon(contentDescription = "Samples", imageVector = Icons.Outlined.List) },
            label = { Text(text = "Samples") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.White.copy(0.4f),
            alwaysShowLabel = true,
            selected = mainBody.value.bottomNavigationViewItem == BottomNavigationViewItem.Samples,
            onClick = {
                mainBody.value = MainBody(BottomNavigationViewItem.Samples)
            }
        )
    }
}

enum class BottomNavigationViewItem {
    Editor, Samples
}