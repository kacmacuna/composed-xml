package readers.imports

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

interface AttributeImports {

    val wrapContentHeight: MemberName
    val wrapContentWidth: MemberName
    val dp: MemberName
    val layoutHeight: MemberName
    val layoutWidth: MemberName
    val graphics: ClassName
    val fillMaxHeight: MemberName
    val fillMaxWidth: MemberName
    val background: MemberName
    val modifier: ClassName
    val colorResource: MemberName

    class Impl(
        private val includePackageName: Boolean
    ) : AttributeImports {
        override val wrapContentHeight: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "wrapContentHeight")
        override val wrapContentWidth: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "wrapContentWidth")
        override val dp: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.ui.unit", "dp")
        override val layoutHeight: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "height")
        override val layoutWidth: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "width")
        override val graphics: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.ui.graphics", "Color")
        override val fillMaxHeight: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "fillMaxHeight")
        override val fillMaxWidth: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "fillMaxWidth")
        override val background: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation", "background")
        override val modifier: ClassName
            get() = Imports.className(includePackageName, "androidx.compose.ui", "Modifier")
        override val colorResource: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.ui.res", "colorResource")

    }
}