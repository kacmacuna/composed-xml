package plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import readers.XmlReader
import readers.XmlReaderImpl


class XmlLayoutToComposable : AnAction() {

    private val xmlLayoutToComposable = XmlNameToComposableName()
    private val xmlReader: XmlReader = XmlReaderImpl()

    override fun actionPerformed(e: AnActionEvent) {
        val descriptor = FileChooserDescriptor(
            true, false, false, false, false, false
        )
        descriptor.title = "To Composed"
        descriptor.description = "Translate xml layout files to composable file"

        FileChooser.chooseFile(descriptor, e.project, null) {
            val isXml = it.extension == "xml"
            val isLayoutFile = it.path.contains("layout")
            if (isXml && isLayoutFile) {
                val layoutFileName = it.path.substring(it.path.lastIndexOf('/') + 1)
                val composedFileName: String = xmlLayoutToComposable.translate(layoutFileName)
                println(composedFileName)
                chooseFileLocation(composedFileName, it, e)
            } else {
                Messages.showErrorDialog(e.project, "You must choose layout xml file", "Error")
            }
        }
    }

    private fun chooseFileLocation(composedFileName: String, layoutFile: VirtualFile?, e: AnActionEvent) {
        if (layoutFile == null) return

        val descriptor = FileChooserDescriptor(
            true, true, false, false, false, false
        )
        descriptor.title = "Generate Compose File"
        descriptor.title = "Generate Compose File From Xml Layout"
        FileChooser.chooseFile(descriptor, e.project, null) {
            val app: Application = ApplicationManager.getApplication()
            app.runWriteAction { createNewFile(layoutFile, composedFileName, it) }
        }
    }

    private fun createNewFile(
        layoutFile: VirtualFile,
        composedFileName: String,
        it: VirtualFile
    ) {
        val fileSpec = xmlReader.read(
            content = layoutFile.contentsToByteArray(),
            fileName = composedFileName
        ).generate()
        fileSpec.writeTo(it.toNioPath())
    }
}