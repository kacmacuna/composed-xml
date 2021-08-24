package plugin

import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import logic.RunWriteAction

class IntelijWriteAction : RunWriteAction {
    override fun execute(action: () -> Unit) {
        val app: Application = ApplicationManager.getApplication()
        app.runWriteAction { action() }
    }
}