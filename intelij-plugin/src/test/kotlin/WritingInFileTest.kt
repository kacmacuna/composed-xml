import com.intellij.openapi.vfs.VirtualFile
import fakes.EmptyRunWriteAction
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import logic.ChooseFile
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.rules.TemporaryFolder
import plugin.TranslatorTranslatorIdeaEvent

@Disabled
class WritingInFileTest {

    @Rule
    var tempFolder = TemporaryFolder()
    val tempFile by lazy {
        tempFolder.newFile("TestComposable.kt")
    }

    private val chooseFile = object : ChooseFile {
        override suspend fun execute(
            chooseFiles: Boolean,
            chooseDirectories: Boolean,
            title: String,
            description: String
        ): VirtualFile {
            return if (chooseDirectories) {
                mockk {
                    every { isDirectory } returns false
                    every { path } returns tempFile.path
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

    @BeforeEach
    fun setUp(){
        tempFolder.create()
        tempFile.writeText("""
            package TestComposable
            
            fun main() {
            
            }
        """.trimIndent())
    }

    @Test
    fun `given translating LinearLayout, generated code should be added in testComposable,kt`() = runBlocking {

        val translatorIdeaEvent = TranslatorTranslatorIdeaEvent(chooseFile, EmptyRunWriteAction)

        translatorIdeaEvent.execute()

        val composableText = tempFile?.readText() ?: ""
        Assertions.assertEquals(
            """
            package TestComposable
            
            fun main() {
            
            }

            @Composable
            public fun Title(): Unit {
              Column () {
              }
            }

        """.trimIndent(), composableText
        )
    }

}