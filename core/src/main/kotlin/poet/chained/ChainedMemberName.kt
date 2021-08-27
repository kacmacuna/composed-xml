package poet.chained

data class ChainedMemberName(
    val prefix: String,
    val argument: String,
    val prefixContainsArguments: Boolean = false
)