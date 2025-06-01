package com.example.codingassessment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader
import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class WriteToTextFileTest {

    private lateinit var context: Context
    private lateinit var textViewModel: TextViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun set() {
        context = ApplicationProvider.getApplicationContext()
        textViewModel = TextViewModel()
        context.deleteFile("text_entries.txt")
    }

    @Test
    fun writeTextEntryToFileTest() {
        textViewModel.addTextEntry("Hello World", context)

        val inputStream = context.openFileInput("text_entries.txt")
        val reader = BufferedReader(InputStreamReader(inputStream)).readText()

        assertTrue("Hello World", reader.contains("Hello World"))
        assertTrue(context.fileList().contains("text_entries.txt"))
    }
    // Asserting a successful write to a text file with the message "Hello World"

    @Test
    fun writeMultipleTextEntriesToFileTest() {
        textViewModel.addTextEntry("First Message", context)
        textViewModel.addTextEntry("Second Message", context)
        textViewModel.addTextEntry("Third Message", context)

        val inputStream = context.openFileInput("text_entries.txt")
        val reader = BufferedReader(InputStreamReader(inputStream)).readText()

        assertTrue("First Message", reader.contains("First Message"))
        assertTrue("Second Message", reader.contains("Second Message"))
        assertTrue("Third Message", reader.contains("Third Message"))
    }
    // Asserting that multiple text entries can be written to a text file.

    @Test
    fun writeSpecialCharactersTextEntryTest() {
        textViewModel.addTextEntry("!@#$%^&*()_+`~[]{};':", context)
        val inputStream = context.openFileInput("text_entries.txt")
        val reader = BufferedReader(InputStreamReader(inputStream)).readText()

        assertTrue("!@#$%^&*()_+`~[]{};':", reader.contains("!@#$%^&*()_+`~[]{};':"))
    }
    // Asserting that special characters can be written to a text file.

    @Test
    fun emptyMessageDoesNotCreateFileTest() {
        textViewModel.addTextEntry("", context)

        assertFalse(context.fileList().contains("text_entries.txt"))

    }
    /* Asserting that an empty message does not initially create a file if there isn't a file
       created.
    */

    @Test
    fun multipleEmptyMessagesDoesNotCreateFileTest() {
        val blankInputs = listOf("", "   ", "\t", "\n", "  \t\n  ")
        blankInputs.forEach {
            textViewModel.addTextEntry(it, context)
        }

        assertFalse(context.fileList().contains("text_entries.txt"))
    }
    /* Self explanatory multiple text entries should not create a text file if there isn't a file
       created.
    */

    @Test
    fun whitespaceOnlyInputDoesNotCreateFileTest() {
        textViewModel.addTextEntry("   ", context)

        assertFalse(context.fileList().contains("text_entries.txt"))
    }
    // Asserting that whitespace only input does not create a file if there isn't a file created.

}