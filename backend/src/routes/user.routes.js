const router = require("express").Router();
const auth = require("../middleware/auth.middleware");
const controller = require("../controllers/user.controller");

// Enable / disable relay consent
router.patch("/relay-consent", auth, controller.updateRelayConsent);

module.exports = router;
