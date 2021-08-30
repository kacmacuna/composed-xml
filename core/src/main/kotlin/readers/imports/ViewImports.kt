package readers.imports

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

interface ViewImports {
    val constraintLayout: ConstraintLayoutImports
    val button: ClassName
    val textField: ClassName
    val box: ClassName
    val column: ClassName
    val row: ClassName
    val text: ClassName

    class Impl(
        private val includePackageName: Boolean
    ) : ViewImports {
        override val constraintLayout: ConstraintLayoutImports
            get() = ConstraintLayoutImports(includePackageName)
        override val button: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.material", "Button")
        override val textField: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.material", "TextField")
        override val box: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.foundation.layout", "Box")
        override val column: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.foundation.layout", "Column")
        override val row: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.foundation.layout", "Row")
        override val text: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.material", "Text")

    }

    class ConstraintLayoutImports(
        private val includePackageName: Boolean
    ) {
        val constrainAs: MemberName
            get() = Imports.memberName(includePackageName, "", "constrainAs")
        val root: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.foundation.layout", "ConstraintLayout")

        val constrainedLayoutReference: MemberName
            get() = Imports.memberName(
                includePackageName,
                "androidx.compose.foundation.layout",
                "ConstrainedLayoutReference"
            )
    }

}

