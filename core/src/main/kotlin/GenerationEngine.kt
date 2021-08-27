import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

class GenerationEngine(
    private val includeFullMemberNames: Boolean
) {

    fun className(
        packageName: String,
        vararg simpleNames: String
    ) = ClassName(if (includeFullMemberNames) packageName else "", simpleNames = simpleNames)

    fun memberName(
        packageName: String,
        simpleNames: String
    ) = MemberName(if (includeFullMemberNames) packageName else "", simpleNames)

    companion object {
        private var INSTANCE: GenerationEngine? = null
        fun createInstance(includeFullMemberNames: Boolean) {
            INSTANCE = GenerationEngine(includeFullMemberNames)
        }

        fun get(): GenerationEngine {
            return INSTANCE ?: error("CoreEngine have not been initialized")
        }
    }

}