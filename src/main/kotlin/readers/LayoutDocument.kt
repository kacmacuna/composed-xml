package readers

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.*

class LayoutDocument(private val document: Document) : Document by document {
    fun getViewIdNameTag(documentElement: Element): String {
        return documentElement.getAttribute("android:id").removePrefix("@+id/")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}