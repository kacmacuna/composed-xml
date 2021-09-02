package readers.imports

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

interface AttributeImports {

    val wrapContentHeight: MemberName
    val wrapContentWidth: MemberName
    val dp: MemberName
    val dimenResource: MemberName
    val stringResource: MemberName
    val layoutHeight: MemberName
    val layoutWidth: MemberName
    val graphics: ClassName
    val fillMaxHeight: MemberName
    val fillMaxWidth: MemberName
    val background: MemberName
    val modifier: ClassName
    val colorResource: MemberName
    val verticalScroll: MemberName
    val horizontalScroll: MemberName
    val rememberScrollState: MemberName

    class Impl(
        private val includePackageName: Boolean
    ) : AttributeImports {
        override val wrapContentHeight: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "wrapContentHeight")
        override val wrapContentWidth: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation.layout", "wrapContentWidth")
        override val dp: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.ui.unit", "dp")
        override val dimenResource: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.ui.res", "dimensionResource")
        override val stringResource: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.ui.res", "stringResource")
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
        override val verticalScroll: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation", "verticalScroll")
        override val horizontalScroll: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation", "horizontalScroll")
        override val rememberScrollState: MemberName
            get() = Imports.memberName(includePackageName, "androidx.compose.foundation", "rememberScrollState")

    }
}