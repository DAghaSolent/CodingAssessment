package com.example.codingassessment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class TextEntry(
    val message: String,
    val timestamp: String
)

class TextViewModel : ViewModel() {
    val textEntries = mutableListOf<TextEntry>()
    val textEntriesLive = MutableLiveData<MutableList<TextEntry>>()
    val currentMessageInput = MutableLiveData<String>()

    val simpleDateFormat = SimpleDateFormat("[HH:mm]", Locale.getDefault())

    fun addTextEntry(message: String, context: Context): String {

        return if (message.isNotBlank()) {

            val currentTime = simpleDateFormat.format(Date())
            /* There are other cleaner options that can be utilised to retrieve the current time in Hour
               and minute format however they are for higher API level 26 and above, to keep the
               consistency for this assessment I have decided to go with an option that will apply with
               the default project API level 24.
            */

            val textEntry = TextEntry(message, currentTime)

            textEntries.add(textEntry)
            textEntriesLive.value = textEntries

            writeToTextFile(context, message, currentTime)
            "Text Entry Successfully Added"
        } else {
            "Text Field cannot be empty. Please enter a message"
        }

    }

    fun writeToTextFile(context: Context, message: String, timestamp: String) {
        try {
            context.openFileOutput("text_entries.txt", Context.MODE_APPEND).use {
                it.write(("Timestamp:${timestamp} / Message: ${message} \n").toByteArray())
            }
            Log.d("FileExample", "Data written successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}