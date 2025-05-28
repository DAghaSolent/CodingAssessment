package com.example.codingassessment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var textAdapter: TextAdapter
    private val textViewModel: TextViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val okButton = findViewById<Button>(R.id.okButton)

        val textEntriesView = findViewById<RecyclerView>(R.id.textEntriesListView)
        textAdapter = TextAdapter(emptyList(), emptyList())
        textEntriesView.layoutManager = LinearLayoutManager(this)
        textEntriesView.adapter = textAdapter

        okButton.setOnClickListener {
            val message = editText.text.toString()
            if (message.isNotEmpty()) {
                textViewModel.addTextEntry(message)
                editText.text.clear()
            }
        }

        textViewModel.textEntriesLive.observe(this, Observer { textEntries ->
            val msgText = textEntries.map{it.message}
            val timestampText = textEntries.map { it.timestamp }

            textAdapter.msgText = msgText
            textAdapter.timestampText = timestampText

            textAdapter.notifyDataSetChanged()
        })
    }
}