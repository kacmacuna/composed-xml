package readers.imports

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

interface Imports {

    val viewImports: ViewImports
    val attributeImports: AttributeImports

    class Impl(
        includePackageNames: Boolean
    ) : Imports {
        override val viewImports: ViewImports = ViewImports.Impl(includePackageNames)
        override val attributeImports: AttributeImports = AttributeImports.Impl(includePackageNames)

    }


    companion object {
        fun memberName(
            includePackageNames: Boolean,
            packageName: String,
            simpleNames: String
        ) = MemberName(if (includePackageNames) packageName else "", simpleNames)

        fun className(
            includePackageNames: Boolean,
            packageName: String,
            vararg simpleNames: String
        ) = ClassName(if (includePackageNames) packageName else "", simpleNames = simpleNames)
    }

}