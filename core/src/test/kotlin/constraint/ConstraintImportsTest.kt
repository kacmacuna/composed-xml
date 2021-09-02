package constraint

import assertions.assertThatAnyImportEquals
import org.junit.jupiter.api.Test
import readers.XmlReaderImpl

class ConstraintImportsTest {

    private val xmlReader = XmlReaderImpl(true)

    @Test
    fun `given marginTop 20dp, dp method should be imported`() {
        val composeGenerator = xmlReader.read(
            content =
            """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <Button
                        android:id="@+id/btn"
                        android:layout_marginTop="20dp"
                        app:layout_constraintTop_toTopOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent(),
            fileName = "test"
        )

        val file = composeGenerator.generate()

        file assertThatAnyImportEquals ServiceLocator.get().imports.attributeImports.dp.canonicalName
    }

}