package samples

data class SampleItem(
    val header: String,
    val body: String
) {
    companion object {
        val DATA = listOf(
            SampleItem(
                "<FrameLayout/>",
                body = """ 
                    |<FrameLayout
                    |    android:id="@+id/vertical"
                    |    android:gravity="bottom|center">
                    |    
                    |    <FrameLayout
                    |        android:id="@+id/horizontal"
                    |        android:gravity="center|end">
                    |    
                    |        <Button
                    |            android:id="@+id/title"
                    |            android:text="Hello"/>
                    |            
                    |    </FrameLayout>
                    |    
                    |</FrameLayout>""".trimIndent().trimMargin()
            ),
            SampleItem(
                "<androidx.constraintlayout.widget.ConstraintLayout/>",
                body = """
                    |<androidx.constraintlayout.widget.ConstraintLayout 
                    |android:id="@+id/content">
                    |
                    |    <TextView
                    |        android:id="@+id/text"
                    |        app:layout_constraintStart_toStartOf="parent"/>
                    |        
                    |    <Button
                    |        android:id="@+id/btn"
                    |        app:layout_constraintStart_toEndOf="@id/text"/>
                    |                
                    |</androidx.constraintlayout.widget.ConstraintLayout>
                """.trimIndent().trimMargin()
            ),
            SampleItem(
                "<LinearLayout\n     android:orientation=\"vertical\"/>",
                body = """ 
                    |<LinearLayout
                    |    android:id="@+id/vertical"
                    |    android:orientation="vertical">
                    |    
                    |    <LinearLayout
                    |    android:id="@+id/horizontal"
                    |    android:orientation="horizontal">
                    |    
                    |        <TextView
                    |            android:id="@+id/title"
                    |            android:text="Hello"/>
                    |            
                    |    </LinearLayout>
                    |    
                    |</LinearLayout>""".trimIndent().trimMargin()
            )
        )
    }
}