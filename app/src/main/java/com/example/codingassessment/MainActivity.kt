package com.example.codingassessment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingassessment.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var textAdapter: TextAdapter
    private val textViewModel: TextViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.textViewModel = textViewModel

        textAdapter = TextAdapter(emptyList(), emptyList())
        binding.textEntriesListView.layoutManager = LinearLayoutManager(this)
        binding.textEntriesListView.adapter = textAdapter

        binding.okButton.setOnClickListener {
            val message = binding.editText.text.toString()
            if (message.isNotEmpty()) {
                textViewModel.addTextEntry(message)
                binding.editText.text.clear()
                textViewModel.writeToTextFile(
                    this, message, textViewModel.simpleDateFormat.format(Date())
                )
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

    override fun onDestroy() {
        super.onDestroy()
        openFileOutput("text_entries.txt", Context.MODE_PRIVATE).use {
            it.write("".toByteArray())
        }
        Log.d("Contents Removed", "Text File Cleared on destroy")
    }
}