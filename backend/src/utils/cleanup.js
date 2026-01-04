const Message = require("../models/Message");
const DeliveryState = require("../models/DeliveryState");

async function cleanupMessages() {
  try {
    // 1️⃣ Remove delivered messages
    const delivered = await DeliveryState.find({ delivered: true });

    const deliveredUuids = delivered.map(d => d.messageUuid);

    if (deliveredUuids.length > 0) {
      await Message.deleteMany({ uuid: { $in: deliveredUuids } });
      await DeliveryState.deleteMany({ messageUuid: { $in: deliveredUuids } });
    }

    // 2️⃣ Remove expired messages (TTL <= 0)
    await Message.deleteMany({ ttl: { $lte: 0 } });

    console.log("🧹 Cleanup job executed");
  } catch (err) {
    console.error("Cleanup error:", err);
  }
}

module.exports = cleanupMessages;
