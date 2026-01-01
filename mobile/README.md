# Mobile Application

Android mobile application built with Kotlin for the Secure Messaging app.

## Setup

1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Ensure you have:
   - Android SDK 26+
   - JDK 11+
   - Kotlin plugin

## Project Structure

```
app/src/main/java/com/capstone/securemessaging/
├── data/
│   ├── model/          # Data models (Message, BluetoothMessage)
│   ├── database/       # Room database setup
│   └── dao/            # Data Access Objects
├── ui/                 # Activities and UI components
├── network/            # API service (TODO)
├── bluetooth/          # Bluetooth module (TODO)
├── encryption/         # Encryption module (TODO)
└── storage/            # Offline storage manager (TODO)
```

## Features to Implement

- [ ] Chat UI
- [ ] Bluetooth communication module
- [ ] Encryption & decryption module
- [ ] Offline message storage (Room database)
- [ ] Consent & settings management
- [ ] Network connectivity detection
- [ ] Message forwarding logic

## Permissions

The app requires:
- Internet access
- Bluetooth permissions
- Location (for Bluetooth scanning on Android 12+)

## Development Notes

- Uses Room for offline storage
- Kotlin Coroutines for async operations
- Retrofit for API communication
- Follows clean architecture principles

