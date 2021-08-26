package assertions

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import org.junit.jupiter.api.Assertions

infix fun FileSpec.assertThatAnyFunctionEquals(expected: String) {
    val actual = members.filterIsInstance<FunSpec>().first().body.toString()
    Assertions.assertEquals(expected, actual)
}