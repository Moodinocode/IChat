package com.capstone.securemessaging.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.securemessaging.R
import com.capstone.securemessaging.data.database.DatabaseModule
import com.capstone.securemessaging.data.model.Conversation
import com.capstone.securemessaging.data.repository.MessagingRepository
import com.capstone.securemessaging.databinding.ActivityMainBinding
import com.capstone.securemessaging.ui.adapter.ConversationAdapter
import com.capstone.securemessaging.ui.viewmodel.ChatListViewModel
import com.capstone.securemessaging.ui.viewmodel.ChatListViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ChatListViewModel
    private lateinit var adapter: ConversationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

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
            ChatListViewModelFactory(repository)
        )[ChatListViewModel::class.java]

        // Setup RecyclerView
        adapter = ConversationAdapter { conversation ->
            openChat(conversation)
        }
        binding.recyclerViewChats.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChats.adapter = adapter

        // Observe conversations
        lifecycleScope.launch {
            viewModel.conversations.collect { conversations ->
                adapter.submitList(conversations)
                if (conversations.isEmpty()) {
                    binding.textViewEmpty.visibility = View.VISIBLE
                    binding.recyclerViewChats.visibility = View.GONE
                } else {
                    binding.textViewEmpty.visibility = View.GONE
                    binding.recyclerViewChats.visibility = View.VISIBLE
                }
            }
        }

        // Setup FAB
        binding.fabAddChat.setOnClickListener {
            startActivity(Intent(this, AddContactsActivity::class.java))
        }
    }

    private fun openChat(conversation: Conversation) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("contactId", conversation.contactId)
            putExtra("contactName", conversation.contactName)
        }
        startActivity(intent)
        viewModel.markAsRead(conversation.conversationId)
    }
}
