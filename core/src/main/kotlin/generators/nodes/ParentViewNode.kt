package generators.nodes

class ParentViewNode(
    viewNode: ViewNode
) : ViewNode by viewNode {

    fun hasAncestors() = ancestors().iterator().hasNext()

    fun ancestors(): Iterable<ViewNode> {
        return ancestorsIterate(mutableListOf(this), this)
    }

    private fun ancestorsIterate(mutableList: MutableList<ViewNode>, viewNode: ViewNode): Iterable<ViewNode> {
        return if (viewNode.parent != null) {
            mutableList.add(viewNode.parent!!)
            ancestorsIterate(mutableList, viewNode.parent!!)
        } else {
            mutableList
        }
    }

}