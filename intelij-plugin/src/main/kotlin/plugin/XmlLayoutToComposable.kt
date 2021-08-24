package plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class XmlLayoutToComposable : AnAction() {

    private val runWriteAction = IntelijWriteAction()
    private val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    override fun actionPerformed(e: AnActionEvent) {
        val chooseFile = IntelijChooseFile(e)
        val ideaEvent = TranslatorTranslatorIdeaEvent(chooseFile, runWriteAction)

        coroutineScope.launch {
            ideaEvent.execute()
        }
    }
}