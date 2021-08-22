package poet.chained

data class ChainedMemberCall(
    val prefix: String,
    val argument: String,
    val prefixContainsArguments: Boolean = false
)