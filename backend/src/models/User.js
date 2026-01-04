const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
  deviceId: { type: String, unique: true, required: true },
  publicKey: { type: String, required: true },
  relayConsent: { type: Boolean, default: false }
}, { timestamps: true });

module.exports = mongoose.model("User", UserSchema);
