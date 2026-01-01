/**
 * Message Routes
 * Handles message sending, receiving, and deduplication
 */

const express = require('express');
const router = express.Router();
const Message = require('../models/Message');
const { v4: uuidv4 } = require('uuid');

// Send a message
router.post('/send', async (req, res) => {
  try {
    const { messageId, senderId, recipientId, encryptedContent } = req.body;

    if (!messageId || !senderId || !recipientId || !encryptedContent) {
      return res.status(400).json({ error: 'Missing required fields' });
    }

    // Check for duplicate message (exactly-once delivery)
    const existingMessage = await Message.findOne({ messageId });
    if (existingMessage) {
      return res.status(200).json({
        message: 'Message already exists (duplicate prevented)',
        messageId: existingMessage.messageId,
      });
    }

    const message = new Message({
      messageId,
      senderId,
      recipientId,
      encryptedContent,
      timestamp: new Date(),
      deliveryStatus: 'pending',
    });

    await message.save();
    res.status(201).json({
      message: 'Message sent successfully',
      messageId: message.messageId,
    });
  } catch (error) {
    console.error('Send message error:', error);
    res.status(500).json({ error: 'Failed to send message' });
  }
});

// Get pending messages for a user
router.get('/pending/:recipientId', async (req, res) => {
  try {
    const messages = await Message.find({
      recipientId: req.params.recipientId,
      deliveryStatus: 'pending',
    }).sort({ timestamp: 1 });

    res.json(messages);
  } catch (error) {
    console.error('Get pending messages error:', error);
    res.status(500).json({ error: 'Failed to get pending messages' });
  }
});

// Mark message as delivered
router.patch('/delivered/:messageId', async (req, res) => {
  try {
    const message = await Message.findOneAndUpdate(
      { messageId: req.params.messageId },
      {
        deliveryStatus: 'delivered',
        deliveredAt: new Date(),
      },
      { new: true }
    );

    if (!message) {
      return res.status(404).json({ error: 'Message not found' });
    }

    res.json({ message: 'Message marked as delivered', messageId: message.messageId });
  } catch (error) {
    console.error('Mark delivered error:', error);
    res.status(500).json({ error: 'Failed to mark message as delivered' });
  }
});

// Mark message as read
router.patch('/read/:messageId', async (req, res) => {
  try {
    const message = await Message.findOneAndUpdate(
      { messageId: req.params.messageId },
      {
        deliveryStatus: 'read',
        readAt: new Date(),
      },
      { new: true }
    );

    if (!message) {
      return res.status(404).json({ error: 'Message not found' });
    }

    res.json({ message: 'Message marked as read', messageId: message.messageId });
  } catch (error) {
    console.error('Mark read error:', error);
    res.status(500).json({ error: 'Failed to mark message as read' });
  }
});

module.exports = router;

