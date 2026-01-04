const mongoose = require("mongoose");

const MessageSchema = new mongoose.Schema(
  {
    uuid: {
      type: String,
      required: true,
      unique: true,
      immutable: true
    },

    senderDeviceId: {
      type: String,
      required: true
    },

    receiverDeviceId: {
      type: String,
      required: true
    },

    encryptedPayload: {
      type: String,
      required: true
    },

    ttl: {
      type: Number,
      required: true
    },

    visitedDevices: {
      type: [String],
      default: []
    },

    uploadedFromOffline: {
      type: Boolean,
      default: false
    }
  },
  { timestamps: true }
);

// Strong deduplication guarantee
MessageSchema.index({ uuid: 1 }, { unique: true });

module.exports = mongoose.model("Message", MessageSchema);
