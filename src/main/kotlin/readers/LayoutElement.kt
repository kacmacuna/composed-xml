package readers

import org.w3c.dom.Element
import java.util.*

class LayoutElement(
    private val element: Element
) : Element by element {

    fun getViewIdNameTag(): String {
        return element.getAttribute("android:id").removePrefix("@+id/")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

}