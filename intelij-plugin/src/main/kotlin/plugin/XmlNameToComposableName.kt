package plugin

class XmlNameToComposableName {

    fun translate(layoutFileName: String) : String{
        return layoutFileName.removeSuffix(".xml").mapIndexed { index: Int, c: Char ->
            if (index == 0 || layoutFileName[index - 1] == '_')
                c.uppercaseChar()
            else
                c
        }.toString().filter { c ->
            c != '_' && c != ',' && c != ']' && c != '[' && c != ' '
        } + "Composable"
    }

}