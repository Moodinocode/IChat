const Message = require("../models/Message");
const DeliveryState = require("../models/DeliveryState");
const User = require("../models/User");

exports.sendMessage = async (req, res) => {
  let {
    uuid,
    senderDeviceId,
    receiverDeviceId,
    encryptedPayload,
    ttl,
    visitedDevices,
    uploadedFromOffline
  } = req.body;

  // 1️⃣ UUID required
  if (!uuid || uuid.trim() === "") {
    return res.status(400).json({ error: "UUID is required" });
  }

  // 2️⃣ TTL must be valid BEFORE decrement
  if (typeof ttl !== "number" || ttl <= 0) {
    return res.status(400).json({ error: "TTL expired" });
  }

  // 🔽 TTL DECREMENT (BACKEND ENFORCED)
  ttl = ttl - 1;

  // 3️⃣ TTL must still be valid AFTER decrement
  if (ttl <= 0) {
    return res.status(400).json({ error: "TTL exhausted after hop" });
  }

  // 4️⃣ Loop prevention
  if (
    Array.isArray(visitedDevices) &&
    visitedDevices.includes(senderDeviceId)
  ) {
    return res.status(400).json({
      error: "Message already processed by this device"
    });
  }

  // 5️⃣ Relay consent enforcement
  if (senderDeviceId !== receiverDeviceId) {
    const sender = await User.findOne({ deviceId: senderDeviceId });

    if (!sender || sender.relayConsent !== true) {
      return res.status(403).json({
        error: "Relay consent not granted"
      });
    }
  }

  // 6️⃣ Deduplication
  const exists = await Message.findOne({ uuid });
  if (exists) {
    return res.status(200).json({ status: "duplicate_ignored" });
  }

  // 7️⃣ Store message with UPDATED TTL
  await Message.create({
    uuid,
    senderDeviceId,
    receiverDeviceId,
    encryptedPayload,
    ttl,
    visitedDevices,
    uploadedFromOffline
  });

  await DeliveryState.create({ messageUuid: uuid });

  res.status(201).json({
    status: "stored",
    remainingTTL: ttl
  });
};

exports.fetchMessages = async (req, res) => {
  const deviceId = req.user.deviceId;

  const messages = await Message.find({
    receiverDeviceId: deviceId
  });

  res.json(messages);
};

exports.acknowledgeDelivery = async (req, res) => {
  const { uuid } = req.body;

  if (!uuid) {
    return res.status(400).json({ error: "UUID required" });
  }

  await DeliveryState.updateOne(
    { messageUuid: uuid },
    {
      delivered: true,
      deliveredAt: new Date()
    }
  );

  res.json({ status: "acknowledged" });
};
