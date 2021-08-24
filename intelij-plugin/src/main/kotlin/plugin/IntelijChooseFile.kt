package plugin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import logic.ChooseFile
import kotlin.coroutines.suspendCoroutine

class IntelijChooseFile(
    private val e: AnActionEvent
) : ChooseFile {

    override suspend fun execute(
        chooseFiles: Boolean,
        chooseDirectories: Boolean,
        title: String,
        description: String
    ): VirtualFile {
        val descriptor = FileChooserDescriptor(
            chooseFiles, chooseDirectories, false, false, false, false
        )

        descriptor.title = title
        descriptor.description = description

        val contentRootForFile = contentRootFile(e)
        descriptor.roots = listOf(contentRootForFile)

        return suspendCoroutine { cont ->
            FileChooser.chooseFile(descriptor, e.project, e.project?.projectFile) {
                cont.resumeWith(Result.success(it))
            }
        }
    }

    private fun contentRootFile(e: AnActionEvent) =
        ProjectFileIndex.getInstance(e.project!!).getContentRootForFile(e.project?.projectFile!!)
}