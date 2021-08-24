import com.intellij.openapi.vfs.VirtualFile
import fakes.EmptyRunWriteAction
import fakes.FakeChooseFile
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import plugin.TranslatorTranslatorIdeaEvent

class LayoutFileValidationTest {


    @Test
    fun `given chosen file path is in drawable folder, IllegalStateException should be thrown`() {
        val extension = "xml"
        val virtualFile = mockk<VirtualFile>()
        every { virtualFile.extension } returns extension
        every { virtualFile.path } returns "drawable/background.$extension"

        val chooseFile = FakeChooseFile(virtualFile)
        val translatorIdeaEvent =
            TranslatorTranslatorIdeaEvent(chooseFile, EmptyRunWriteAction)

        val exc = assertThrows<IllegalStateException> {
            runBlocking { translatorIdeaEvent.execute() }
        }
        assertTrue(exc.message?.contains(virtualFile.path) == true)
    }

    @Test
    fun `given chosen file's extension is not xml, IllegalStateException should be thrown`() {
        val extension = "kt"
        val virtualFile = mockk<VirtualFile>()
        every { virtualFile.extension } returns extension
        every { virtualFile.path } returns "layout/background.$extension"

        val chooseFile = FakeChooseFile(virtualFile)

        val translatorIdeaEvent = TranslatorTranslatorIdeaEvent(chooseFile, EmptyRunWriteAction)
        val exc = assertThrows<IllegalStateException> {
            runBlocking { translatorIdeaEvent.execute() }
        }
        assertTrue(exc.message?.contains(virtualFile.path) == true)
    }

}