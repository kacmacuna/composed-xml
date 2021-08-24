package logic

import com.intellij.openapi.vfs.VirtualFile

interface TranslatorIdeaEvent {

    suspend fun execute()

}