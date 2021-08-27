package poet.chained

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName

data class ChainedMemberName(
    val prefix: MemberName,
    val argument: CodeBlock,
    val containsArguments: Boolean = true
)