package generators.nodes.elements.colors

class ColorElementParser {

    fun parse(input: String): ColorElement {
        return if (input.contains("@color/"))
            ColorElementResource(input)
        else if (input.contains("#"))
            ColorElementHardCoded(input)
        else if (input.contains("?attr/") || input.contains("?/"))
            ColorElementTheme()
        else if (input.isEmpty())
            ColorElementEmpty
        else
            throw IllegalStateException("Invalid Color: $input")
    }

}