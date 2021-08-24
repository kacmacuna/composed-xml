package fakes

import logic.RunWriteAction

object EmptyRunWriteAction : RunWriteAction{
    override fun execute(action: () -> Unit) {
        action()
    }


}