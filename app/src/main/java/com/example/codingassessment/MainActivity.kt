package com.example.codingassessment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
            val message = binding.editText.text.toString().trim()
            if (message.isNotBlank()) {
                textViewModel.addTextEntry(message)
                binding.editText.text.clear()
                textViewModel.writeToTextFile(
                    this, message, textViewModel.simpleDateFormat.format(Date())
                )
                Toast.makeText(this, "Text Entry Successfully Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Text Field cannot be empty. Please enter a message",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        textViewModel.textEntriesLive.observe(this, Observer { textEntries ->
            val msgText = textEntries.map { it.message }
            val timestampText = textEntries.map { it.timestamp }

            textAdapter.msgText = msgText
            textAdapter.timestampText = timestampText

            textAdapter.notifyDataSetChanged()
        })
    }

}