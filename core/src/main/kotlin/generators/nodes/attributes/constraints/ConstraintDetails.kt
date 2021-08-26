package generators.nodes.attributes.constraints

data class ConstraintDetails(
    val constraintDirection: ConstraintDirection,
    val constraintToId: String,
    val constraintToDirection: ConstraintDirection
)