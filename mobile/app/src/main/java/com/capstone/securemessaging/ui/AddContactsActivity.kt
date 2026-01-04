package com.capstone.securemessaging.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.securemessaging.R
import com.capstone.securemessaging.data.database.DatabaseModule
import com.capstone.securemessaging.data.model.Contact
import com.capstone.securemessaging.data.model.Conversation
import com.capstone.securemessaging.data.repository.MessagingRepository
import com.capstone.securemessaging.databinding.ActivityAddContactsBinding
import com.capstone.securemessaging.ui.adapter.ContactAdapter
import com.capstone.securemessaging.ui.viewmodel.ContactsViewModel
import com.capstone.securemessaging.ui.viewmodel.ContactsViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class AddContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactsBinding
    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            pickContact()
        } else {
            Toast.makeText(this, "Permission needed to access contacts", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickContactLauncher = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { uri ->
        uri?.let {
            val contact = getContactFromUri(it)
            contact?.let { c ->
                viewModel.addContact(c)
                Toast.makeText(this, "Contact added: ${c.username}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
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
            ContactsViewModelFactory(repository)
        )[ContactsViewModel::class.java]

        // Setup RecyclerView
        adapter = ContactAdapter { contact ->
            startChatWithContact(contact, repository)
        }
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewContacts.adapter = adapter

        // Observe contacts
        lifecycleScope.launch {
            viewModel.contacts.collect { contacts ->
                adapter.submitList(contacts)
            }
        }

        // Setup pick from contacts button
        binding.buttonPickFromContacts.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                pickContact()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }

        // Setup add contact button
        binding.buttonAddContact.setOnClickListener {
            val userId = binding.editTextUserId.text.toString().trim()
            val username = binding.editTextUsername.text.toString().trim()
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()

            if (userId.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "User ID and Username are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val contact = Contact(
                userId = userId,
                username = username,
                phoneNumber = if (phoneNumber.isEmpty()) null else phoneNumber
            )

            viewModel.addContact(contact)
            Toast.makeText(this, "Contact added: $username", Toast.LENGTH_SHORT).show()

            // Clear fields
            binding.editTextUserId.text?.clear()
            binding.editTextUsername.text?.clear()
            binding.editTextPhoneNumber.text?.clear()
        }
    }

    private fun pickContact() {
        pickContactLauncher.launch(null)
    }

    private fun getContactFromUri(uri: android.net.Uri): Contact? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val name = it.getString(nameIndex) ?: "Unknown"
                val phone = if (phoneIndex >= 0) it.getString(phoneIndex) else null

                return Contact(
                    userId = UUID.randomUUID().toString(),
                    username = name,
                    phoneNumber = phone
                )
            }
        }
        return null
    }

    private fun startChatWithContact(contact: Contact, repository: MessagingRepository) {
        CoroutineScope(Dispatchers.IO).launch {
            // Create or get conversation
            var conversation = repository.getConversationByContactId(contact.userId)
            if (conversation == null) {
                conversation = Conversation(
                    conversationId = UUID.randomUUID().toString(),
                    contactId = contact.userId,
                    contactName = contact.username,
                    profileImageUri = contact.profileImageUri
                )
                repository.insertOrUpdateConversation(conversation)
            }

            // Start chat activity
            runOnUiThread {
                val intent = Intent(this@AddContactsActivity, ChatActivity::class.java).apply {
                    putExtra("contactId", contact.userId)
                    putExtra("contactName", contact.username)
                }
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

