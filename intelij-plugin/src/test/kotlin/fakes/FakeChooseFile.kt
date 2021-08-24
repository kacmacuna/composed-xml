package fakes

import com.intellij.openapi.vfs.VirtualFile
import logic.ChooseFile

class FakeChooseFile(private val virtualFile: VirtualFile) : ChooseFile {
    override suspend fun execute(
        chooseFiles: Boolean,
        chooseDirectories: Boolean,
        title: String,
        description: String
    ): VirtualFile {
        return virtualFile
    }
}