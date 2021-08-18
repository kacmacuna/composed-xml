package generators.nodes.attributes.colors

class ColorAttributeParser {

    fun parse(input: String): ColorAttribute {
        return if (input.contains("@color/"))
            ColorAttributeResource(input)
        else if (input.contains("#"))
            ColorAttributeHardCoded(input)
        else if (input.contains("?attr/") || input.contains("?/"))
            ColorAttributeTheme()
        else if (input.isEmpty())
            ColorAttributeEmpty
        else
            throw IllegalStateException("Invalid Color: $input")
    }

}