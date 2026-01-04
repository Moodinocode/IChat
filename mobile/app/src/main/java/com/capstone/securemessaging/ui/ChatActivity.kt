package com.capstone.securemessaging.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.securemessaging.R
import com.capstone.securemessaging.data.database.DatabaseModule
import com.capstone.securemessaging.data.repository.MessagingRepository
import com.capstone.securemessaging.databinding.ActivityChatBinding
import com.capstone.securemessaging.ui.adapter.MessageAdapter
import com.capstone.securemessaging.ui.viewmodel.ChatViewModel
import com.capstone.securemessaging.ui.viewmodel.ChatViewModelFactory
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: MessageAdapter
    private var recipientId: String = ""
    private var contactName: String = ""
    private val currentUserId = "user_${android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.ANDROID_ID)}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact info from intent
        recipientId = intent.getStringExtra("contactId") ?: ""
        contactName = intent.getStringExtra("contactName") ?: "Unknown"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = contactName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize database and repository
        val database = DatabaseModule.getDatabase(this)
        val repository = MessagingRepository(
            database.contactDao(),
            database.conversationDao(),
            database.messageDao()
        )

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            ChatViewModelFactory(repository, currentUserId, recipientId, contactName)
        )[ChatViewModel::class.java]

        // Setup RecyclerView
        adapter = MessageAdapter(currentUserId)
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        binding.recyclerViewMessages.adapter = adapter

        // Observe messages
        lifecycleScope.launch {
            viewModel.messages.collect { messages ->
                adapter.submitList(messages)
                if (messages.isNotEmpty()) {
                    binding.recyclerViewMessages.smoothScrollToPosition(messages.size - 1)
                }
            }
        }

        // Setup send button
        binding.buttonSend.setOnClickListener {
            val messageText = binding.editTextMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                viewModel.sendMessage(messageText)
                binding.editTextMessage.text?.clear()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

