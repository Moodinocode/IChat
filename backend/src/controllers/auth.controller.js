const User = require("../models/User");
const jwt = require("jsonwebtoken");

exports.register = async (req, res) => {
  const { deviceId, publicKey } = req.body;

  const user = await User.create({ deviceId, publicKey });
  const token = jwt.sign({ deviceId }, process.env.JWT_SECRET);

  res.json({ token });
};
