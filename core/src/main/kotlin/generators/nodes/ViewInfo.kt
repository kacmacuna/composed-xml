package generators.nodes

import generators.nodes.attributes.layout.LayoutHeight
import generators.nodes.attributes.layout.LayoutWidth
import poet.chained.ChainedMemberName

interface ViewInfo {
    val id: ViewId
    val width: LayoutWidth
    val height: LayoutHeight
    val chainedMemberNames: List<ChainedMemberName>
}