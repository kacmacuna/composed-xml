# composed-xml

## Inspired by - [Recompose](https://github.com/pocmo/recompose)

## Docs
* [Supported View Types](docs/SUPPORTED_VIEW_TYPES.md)
* [Desktop App Screens](desktop/screens)

## Examples
```xml
<androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/content"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <TextView
            android:id="@+id/text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Current Number: 0"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent"/>

    <Button
            android:id="@+id/btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Increase"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toEndOf="@id/text"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

===>

```kotlin
package temp

import androidx.compose.foundation.layout.ConstrainedLayoutReference
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlin.Unit

@Composable
public fun Content(): Unit {
    ConstraintLayout (modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        val textRef = ConstrainedLayoutReference(Any())
        val btnRef = ConstrainedLayoutReference(Any())
        Text("Current Number: 0", modifier =
        Modifier.wrapContentWidth().wrapContentHeight().constrainAs(textRef, {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        ))
        Button(onClick = {}, modifier =
        Modifier.wrapContentWidth().wrapContentHeight().constrainAs(btnRef, {
            top.linkTo(parent.top)
            start.linkTo(textRef.end)
        }
        )) {
            Text("Increase", modifier = Modifier.wrapContentWidth().wrapContentHeight())
        }
    }
}
```