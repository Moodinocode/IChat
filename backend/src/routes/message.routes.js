const router = require("express").Router();
const auth = require("../middleware/auth.middleware");
const controller = require("../controllers/message.controller");

router.post("/", auth, controller.sendMessage);
router.get("/", auth, controller.fetchMessages);
router.post("/ack", auth, controller.acknowledgeDelivery);

module.exports = router;
