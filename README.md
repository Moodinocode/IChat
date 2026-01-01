# Secure Mobile Messaging App - Capstone Project

An AI-assisted, offline-capable, secure mobile messaging application with Bluetooth-based message forwarding.

## Project Structure

```
.
├── backend/          # Node.js/Express backend server
├── mobile/           # Android mobile application (Kotlin)
├── docs/             # Additional documentation
└── requirements.md   # Project requirements document
```

## Features

- **Real-time Chat**: WhatsApp-like messaging functionality
- **Hybrid Connectivity**: Internet (Wi-Fi/Mobile Data) and Bluetooth forwarding
- **Offline Support**: Temporary encrypted storage with guaranteed delivery
- **End-to-End Encryption**: Secure communication with encrypted payloads
- **Loop Prevention**: TTL and device tracking to prevent infinite message hopping
- **User Consent**: Explicit control over message relaying
- **Exactly-Once Delivery**: No duplicates, no message loss

## Technology Stack

- **Backend**: Node.js, Express.js, MongoDB
- **Mobile**: Android (Kotlin)
- **Database**: MongoDB

## Getting Started

### Prerequisites

- Node.js (v18+)
- MongoDB (local or cloud instance)
- Android Studio
- JDK 11+

### Backend Setup

```bash
cd backend
npm install
npm start
```

### Mobile Setup

1. Open `mobile/` in Android Studio
2. Sync Gradle dependencies
3. Run on an Android device or emulator

## Development Principles

This project follows strict clean code principles:
- DRY (Don't Repeat Yourself)
- KISS (Keep It Simple, Stupid)
- YAGNI (You Aren't Gonna Need It)
- Separation of Concerns
- Single Responsibility Principle
- Modularity and Reusability
- Meaningful Naming and Consistency

## License

Capstone Project - Educational Use

