package generators.nodes

@JvmInline
value class ViewId(
    private val value: String,
) {

    fun getIdOrDefault(): String {
        return value.ifEmpty {
            DEFAULT
        }
    }

    companion object {
        const val DEFAULT = "Content"
    }

}