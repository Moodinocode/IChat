const User = require("../models/User");

exports.updateRelayConsent = async (req, res) => {
  const { relayConsent } = req.body;
  const deviceId = req.user.deviceId;

  if (typeof relayConsent !== "boolean") {
    return res.status(400).json({
      error: "relayConsent must be true or false"
    });
  }

  const user = await User.findOneAndUpdate(
    { deviceId },
    { relayConsent },
    { new: true }
  );

  if (!user) {
    return res.status(404).json({ error: "User not found" });
  }

  res.json({
    status: "updated",
    relayConsent: user.relayConsent
  });
};
