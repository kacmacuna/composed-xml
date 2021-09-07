package data

object XmlData {

    object ConstraintLayout {
        const val EMPTY_BODY = """<androidx.constraintlayout.widget.ConstraintLayout android:id="@+id/content" />"""
        val BUTTON_TOP_TOP_PARENT = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <Button
                        android:id="@+id/btn"
                        app:layout_constraintTop_toTopOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_END_END_PARENT_AND_BOTTOM_BOTTOM_PARENT = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/txt"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_START_START_PARENT_AND_BTN_START_END_TEXT = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        app:layout_constraintStart_toStartOf="parent"/>
                        
                    <Button
                        android:id="@+id/btn"
                        app:layout_constraintStart_toEndOf="@id/text"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_START_START_PARENT_WITH_MARGIN_20_DP = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginStart="20dp"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_START_START_PARENT_WITH_MARGIN_RES_20_DP = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginStart="@dimen/dp_20"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_WITH_SEVERAL_SAME_MARGINS = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginStart="@dimen/dp_20"
                        android:layout_marginEnd="@dimen/dp_20"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
        val TEXT_WITH_MARGIN_HORIZONTAL = """
                <androidx.constraintlayout.widget.ConstraintLayout 
                    android:id="@+id/content">
                    
                    <TextView
                        android:id="@+id/text"
                        android:layout_marginHorizontal="20dp"
                        app:layout_constraintStart_toStartOf="parent"/>
                                    
                </androidx.constraintlayout.widget.ConstraintLayout>
            """.trimIndent()
    }

}