package com.example.codingassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TextViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var textViewModel: TextViewModel

    @Before
    fun setup() {
        textViewModel = TextViewModel()
    }

    @Test
    fun addTextEntryTest() {
        textViewModel.addTextEntry("Hello World")

        assertEquals(1, textViewModel.textEntries.size)
        // Asserting that the message has been added to the list within the ViewModel.

        assertTrue(textViewModel.textEntries[0].message == "Hello World")
        // Asserting that the first message added is the message that I have added.

        assertEquals(1, textViewModel.textEntriesLive.value?.size)
        /* Asserting that the Live Data list within the Viewmodel has been updated and updated the
           size of the LiveData List.
        */

        assertNotNull(textViewModel.textEntriesLive.value)
        // Asserting that the LiveData List is not null.
    }

    @Test
    fun addTextEntryWithFormattedTimestampTest() {
        textViewModel.addTextEntry("Hello World")
        val textEntry = textViewModel.textEntriesLive.value?.get(0)
        val timestamp = textEntry?.timestamp

        val regex = """\[\d{2}:\d{2}]""".toRegex()

        assertTrue(timestamp?.matches(regex) == true)
        // Asserting that the timestamp attached to a text entry is in the correct format.
    }

    @Test
    fun addMultipleTextEntriesTest() {
        textViewModel.addTextEntry("First Message")
        textViewModel.addTextEntry("Second Message")

        assertNotNull(textViewModel.textEntriesLive.value)
        // Asserting that the LiveData List is not null.

        assertEquals(2, textViewModel.textEntriesLive.value?.size)
        // Asserting that the size of the LiveData List is 2 and has 2 message text entries.

        assertEquals("First Message", textViewModel.textEntriesLive.value?.get(0)?.message)
        // Asserting that the first message added is the message that I have added within this test.

        assertEquals("Second Message", textViewModel.textEntriesLive.value?.get(1)?.message)
        /* Asserting that the second message added is the message that I have added within this
           test.
        */
    }

    @Test
    fun addTextWithSpecialCharactersTest() {
        textViewModel.addTextEntry("!@#$%^&*()_+`~[]{};':\",./<>?")

        assertEquals(
            "!@#$%^&*()_+`~[]{};':\",./<>?",
            textViewModel.textEntriesLive.value?.get(0)?.message
        )
        // Asserting that the message with special characters is added correctly.
    }

    @Test
    fun testTimestampEqualsLocalTimeTest() {
        textViewModel.addTextEntry("Hello World")
        val simpleDataFormat = SimpleDateFormat("[HH:mm]", Locale.getDefault())

        assertEquals(
            simpleDataFormat.format(Date()),
            textViewModel.textEntriesLive.value?.get(0)?.timestamp
        )
        // Asserting that when a text entry is added the timestamp equals the local time.
    }

}