package poet

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

fun FunSpec.Builder.addComposeAnnotation() = addAnnotation(
    AnnotationSpec.builder(
        ClassName("androidx.compose.runtime", "Composable")
    ).build()
)