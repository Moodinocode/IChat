package com.capstone.securemessaging.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.securemessaging.R
import com.capstone.securemessaging.data.model.Conversation
import java.text.SimpleDateFormat
import java.util.*

class ConversationAdapter(
    private val onItemClick: (Conversation) -> Unit
) : ListAdapter<Conversation, ConversationAdapter.ConversationViewHolder>(ConversationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName: TextView = itemView.findViewById(R.id.textViewContactName)
        private val lastMessage: TextView = itemView.findViewById(R.id.textViewLastMessage)
        private val timestamp: TextView = itemView.findViewById(R.id.textViewTimestamp)
        private val unreadCount: TextView = itemView.findViewById(R.id.textViewUnreadCount)

        fun bind(conversation: Conversation) {
            contactName.text = conversation.contactName
            lastMessage.text = conversation.lastMessage ?: "No messages"
            
            // Format timestamp
            val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            timestamp.text = dateFormat.format(Date(conversation.lastMessageTimestamp))
            
            // Show unread count if > 0
            if (conversation.unreadCount > 0) {
                unreadCount.text = conversation.unreadCount.toString()
                unreadCount.visibility = View.VISIBLE
            } else {
                unreadCount.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onItemClick(conversation)
            }
        }
    }

    class ConversationDiffCallback : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.conversationId == newItem.conversationId
        }

        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem == newItem
        }
    }
}

