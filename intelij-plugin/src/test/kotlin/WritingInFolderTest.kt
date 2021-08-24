import com.intellij.openapi.vfs.VirtualFile
import fakes.EmptyRunWriteAction
import fakes.FakeChooseFile
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import logic.ChooseFile
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder
import plugin.TranslatorTranslatorIdeaEvent


class WritingInFolderTest {

    @Rule
    var tempFolder = TemporaryFolder()

    private val chooseFile = object : ChooseFile {
        override suspend fun execute(
            chooseFiles: Boolean,
            chooseDirectories: Boolean,
            title: String,
            description: String
        ): VirtualFile {
            return if (chooseDirectories) {
                mockk {
                    every { toNioPath() } returns tempFolder.root.toPath()
                }
            } else {
                provideLayoutFile()
            }
        }

        private fun provideLayoutFile(): VirtualFile {
            val extension = "xml"
            val virtualFile = mockk<VirtualFile>()
            every { virtualFile.extension } returns extension
            every { virtualFile.path } returns "layout/test.$extension"
            every { virtualFile.contentsToByteArray() } returns """ 
            <LinearLayout
                android:id="@+id/title"
                android:orientation="vertical" /> """.trimIndent().toByteArray()
            return virtualFile
        }

    }

    @Test
    fun `given translating LinearLayout, generated code should be pasted in testComposable`() = runBlocking {
        tempFolder.create()
        val translatorIdeaEvent = TranslatorTranslatorIdeaEvent(chooseFile, EmptyRunWriteAction)

        translatorIdeaEvent.execute()

        val generatedComposableFile = tempFolder.root.listFiles()?.first()?.listFiles()?.first()
        val composableText = generatedComposableFile?.readText() ?: ""
        Assertions.assertEquals(
            """
            package TestComposable

            import androidx.compose.foundation.layout.Column
            import androidx.compose.runtime.Composable
            import kotlin.Unit

            @Composable
            public fun Title(): Unit {
              Column () {
              }
            }

        """.trimIndent(), composableText
        )
    }


}