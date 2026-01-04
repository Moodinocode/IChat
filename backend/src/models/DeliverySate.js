const mongoose = require("mongoose");

const DeliveryStateSchema = new mongoose.Schema({
  messageUuid: { type: String, required: true },
  delivered: { type: Boolean, default: false },
  deliveredAt: { type: Date }
});

module.exports = mongoose.model("DeliveryState", DeliveryStateSchema);
