package com.capstone.securemessaging.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.securemessaging.R
import com.capstone.securemessaging.data.model.Message
import com.capstone.securemessaging.data.model.DeliveryStatus
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val currentUserId: String
) : ListAdapter<Message, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageContent: TextView = itemView.findViewById(R.id.textViewMessageContent)
        private val timestamp: TextView = itemView.findViewById(R.id.textViewTimestamp)
        private val statusIcon: ImageView = itemView.findViewById(R.id.imageViewStatus)
        private val messageBubble: View = itemView.findViewById(R.id.layoutMessageBubble)

        fun bind(message: Message) {
            // TODO: Decrypt message content
            messageContent.text = message.encryptedContent // Placeholder
            
            // Format timestamp
            val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            timestamp.text = dateFormat.format(Date(message.timestamp))
            
            // Show status icon based on delivery status
            val isSent = message.senderId == currentUserId
            if (isSent) {
                when (message.deliveryStatus) {
                    DeliveryStatus.PENDING -> {
                        statusIcon.setImageResource(android.R.drawable.ic_menu_recent_history)
                        statusIcon.alpha = 0.5f
                    }
                    DeliveryStatus.DELIVERED -> {
                        statusIcon.setImageResource(android.R.drawable.checkbox_on_background)
                        statusIcon.alpha = 0.7f
                    }
                    DeliveryStatus.READ -> {
                        statusIcon.setImageResource(android.R.drawable.checkbox_on_background)
                        statusIcon.alpha = 1.0f
                    }
                }
                statusIcon.visibility = View.VISIBLE
            } else {
                statusIcon.visibility = View.GONE
            }
            
            // Align message bubble based on sender
            val spacerStart = itemView.findViewById<View>(R.id.viewSpacerStart)
            val spacerEnd = itemView.findViewById<View>(R.id.viewSpacerEnd)
            
            if (isSent) {
                val startParams = spacerStart.layoutParams
                startParams.width = 0
                startParams.height = 1
                spacerStart.layoutParams = startParams
                
                val endParams = spacerEnd.layoutParams
                endParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                endParams.height = 1
                spacerEnd.layoutParams = endParams
                
                messageBubble.setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
            } else {
                val startParams = spacerStart.layoutParams
                startParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                startParams.height = 1
                spacerStart.layoutParams = startParams
                
                val endParams = spacerEnd.layoutParams
                endParams.width = 0
                endParams.height = 1
                spacerEnd.layoutParams = endParams
                
                messageBubble.setBackgroundResource(android.R.drawable.dialog_holo_dark_frame)
            }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.messageId == newItem.messageId
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}

