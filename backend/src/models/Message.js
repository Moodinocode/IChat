/**
 * Message Model
 * Represents a message in the system with deduplication support
 */

const mongoose = require('mongoose');
const { Schema } = mongoose;

const messageSchema = new Schema({
  messageId: {
    type: String,
    required: true,
    unique: true,
    index: true,
  },
  senderId: {
    type: String,
    required: true,
    index: true,
  },
  recipientId: {
    type: String,
    required: true,
    index: true,
  },
  encryptedContent: {
    type: String,
    required: true,
  },
  timestamp: {
    type: Date,
    default: Date.now,
    index: true,
  },
  deliveryStatus: {
    type: String,
    enum: ['pending', 'delivered', 'read'],
    default: 'pending',
    index: true,
  },
  deliveredAt: {
    type: Date,
  },
  readAt: {
    type: Date,
  },
});

// Compound index for efficient queries
messageSchema.index({ recipientId: 1, deliveryStatus: 1 });
messageSchema.index({ senderId: 1, timestamp: -1 });

module.exports = mongoose.model('Message', messageSchema);

