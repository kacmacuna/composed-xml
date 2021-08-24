package logic

interface RunWriteAction {

    fun execute(action: () -> Unit)

}