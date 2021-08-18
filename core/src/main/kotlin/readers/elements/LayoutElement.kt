package readers.elements

import generators.nodes.ViewNode
import generators.nodes.attributes.Alignment
import org.w3c.dom.Element
import java.util.*

abstract class LayoutElement<out T : ViewNode>(
    val originalElement: Element
) : Element by originalElement {

    protected fun getViewIdNameTag(): String {
        return originalElement.getAttribute("android:id").removePrefix("@+id/")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    protected fun getAlignmentFromGravity(): Alignment {
        return when(getAttribute("android:gravity")){
            "top|start", "start|top" -> Alignment.TopStart
            "center" -> Alignment.Center
            "bottom|center", "center|bottom" -> Alignment.BottomCenter
            "center|end", "end|center" -> Alignment.CenterEnd
            "top|center", "center|top" -> Alignment.TopCenter
            "top|end", "end|top" -> Alignment.TopEnd
            "center|start", "start|center" -> Alignment.CenterStart
            "bottom|start", "start|bottom" -> Alignment.BottomStart
            "bottom|end", "end|bottom" -> Alignment.BottomEnd
            "top" -> Alignment.Top
            "center_vertically" -> Alignment.CenterVertically
            "bottom" -> Alignment.Bottom
            "start", "left" -> Alignment.Start
            "end", "right" -> Alignment.End
            "center_horizontally" -> Alignment.CenterHorizontally
            "" -> Alignment.NoAlignment
            else -> TODO("Unhandled gravity types")
        }
    }

    abstract fun node(): T

}