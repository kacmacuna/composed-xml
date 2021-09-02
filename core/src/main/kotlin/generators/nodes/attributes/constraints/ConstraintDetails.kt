package generators.nodes.attributes.constraints

import generators.nodes.attributes.size.DPAttribute

data class ConstraintDetails(
    val constraintDirection: ConstraintDirection,
    val constraintToId: String,
    val constraintToDirection: ConstraintDirection,
    val margin: DPAttribute
)