package assertions

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions

fun FileSpec.assertThatAnyFunctionEquals(expected: String) {
    assertThat(
        members.filterIsInstance<FunSpec>().map { it.body.toString() },
        hasItem(expected)
    )
}