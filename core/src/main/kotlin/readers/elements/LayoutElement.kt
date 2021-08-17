package readers.elements

import generators.nodes.ViewNode
import org.w3c.dom.Element
import java.util.*

abstract class LayoutElement<out T : ViewNode>(
    val originalElement: Element
) : Element by originalElement {

    fun getViewIdNameTag(): String {
        return originalElement.getAttribute("android:id").removePrefix("@+id/")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    abstract fun node(): T

}