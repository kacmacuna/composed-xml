package assertions

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions

infix fun FileSpec.assertThatAnyFunctionEquals(expected: String) {
    val actual = members.filterIsInstance<FunSpec>().first().body.toString()
    Assertions.assertEquals(expected, actual)
}

infix fun FileSpec.assertThatAnyImportEquals(expected: String) {
    assertThat(
        toString(),
        CoreMatchers.containsString("import $expected")
    )
}