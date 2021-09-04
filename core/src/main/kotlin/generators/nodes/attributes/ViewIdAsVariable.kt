package generators.nodes.attributes

@JvmInline
value class ViewIdAsVariable(
    private val value: String
) {
    override fun toString(): String {
        return value.xmlValueAsVariable()
    }
}