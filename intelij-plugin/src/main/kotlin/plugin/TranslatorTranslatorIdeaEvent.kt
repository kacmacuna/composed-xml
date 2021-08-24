package plugin

import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.VirtualFile
import com.squareup.kotlinpoet.FileSpec
import kotlinx.coroutines.*
import logic.ChooseFile
import logic.RunWriteAction
import logic.TranslatorIdeaEvent
import readers.XmlReader
import readers.XmlReaderImpl
import java.io.File
import kotlin.coroutines.CoroutineContext

class TranslatorTranslatorIdeaEvent(
    private val chooseFile: ChooseFile,
    private val runWriteAction: RunWriteAction
) : TranslatorIdeaEvent {

    private val xmlLayoutToComposable = XmlNameToComposableName()
    private val xmlReader: XmlReader = XmlReaderImpl()


    override suspend fun execute() {

        val chooseLayoutFile = chooseFile.execute(
            chooseFiles = true,
            chooseDirectories = false,
            "To Composed",
            "Translate xml layout files to composable file"
        )
        val isXml = chooseLayoutFile.extension == "xml"
        val isLayoutFile = chooseLayoutFile.path.contains("layout")
        if (isXml && isLayoutFile) {
            val layoutFileName = chooseLayoutFile.path.substring(chooseLayoutFile.path.lastIndexOf('/') + 1)
            val composedFileName: String = xmlLayoutToComposable.translate(layoutFileName)
            chooseFileLocation(composedFileName, chooseLayoutFile)
        } else {
            throw IllegalStateException("You must choose layout xml file, current layout:${chooseLayoutFile.path}")
        }
    }

    private suspend fun chooseFileLocation(composedFileName: String, layoutFile: VirtualFile?) {
        if (layoutFile == null) return

        val virtualFile = chooseFile.execute(
            chooseFiles = true,
            chooseDirectories = true,
            title = "Generate Compose File From Xml Layout",
            description = ""
        )

        runWriteAction.execute {
            createNewFile(layoutFile, composedFileName, virtualFile)
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
        if (it.isDirectory) {
            fileSpec.writeTo(it.toNioPath())
        } else {
            throw TODO("This functionality is not yet implemented")
        }
    }

}