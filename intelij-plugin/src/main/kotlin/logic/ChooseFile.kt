package logic

import com.intellij.openapi.vfs.VirtualFile

interface ChooseFile {

    suspend fun execute(
        chooseFiles: Boolean,
        chooseDirectories: Boolean,
        title: String,
        description: String
    ): VirtualFile

}