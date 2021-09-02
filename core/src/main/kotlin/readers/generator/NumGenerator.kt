package readers.generator

interface NumGenerator {

    fun generate(): Int
    
    class Impl(
        private val range: IntRange
    ): NumGenerator {
        override fun generate(): Int {
            return range.random()
        }

    }

}