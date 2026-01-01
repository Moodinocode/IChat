/**
 * User Model
 * Represents a user in the messaging system
 */

const mongoose = require('mongoose');
const { Schema } = mongoose;

const userSchema = new Schema({
  userId: {
    type: String,
    required: true,
    unique: true,
    index: true,
  },
  deviceId: {
    type: String,
    required: true,
    unique: true,
    index: true,
  },
  username: {
    type: String,
    required: true,
  },
  publicKey: {
    type: String,
    required: true,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
  lastSeen: {
    type: Date,
    default: Date.now,
  },
});

// Index for faster queries
userSchema.index({ userId: 1 });
userSchema.index({ deviceId: 1 });

module.exports = mongoose.model('User', userSchema);

