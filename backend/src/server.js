require("dotenv").config();
const express = require("express");
const connectDB = require("./config/db");
const cleanupMessages = require("./utils/cleanup");

const app = express();
connectDB();

app.use(express.json());

app.use("/api/auth", require("./routes/auth.routes"));
app.use("/api/messages", require("./routes/message.routes"));
app.use("/api/users", require("./routes/user.routes"));

// Cleanup every 5 minutes
setInterval(cleanupMessages, 5 * 60 * 1000);

app.listen(3000, () => {
  console.log("🚀 Server running on port 3000");
});
