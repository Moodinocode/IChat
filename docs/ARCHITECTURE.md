# Architecture Overview

## System Components

### 1. Mobile Client (Android/Kotlin)

#### Modules:
- **UI Layer**: Activities, Fragments, ViewModels
- **Data Layer**: 
  - Room Database (offline storage)
  - Network API client
  - Local data models
- **Bluetooth Module**: 
  - Device discovery
  - Message forwarding
  - TTL and loop prevention
- **Encryption Module**: 
  - End-to-end encryption
  - Key management
- **Storage Manager**: 
  - Offline message queue
  - Sync with backend

### 2. Backend Server (Node.js/Express)

#### Components:
- **REST API**: Express routes
- **Database Layer**: Mongoose models
- **Message Deduplication**: UUID-based
- **Delivery Tracking**: Status management

### 3. Database (MongoDB)

#### Collections:
- **Users**: User profiles, device IDs, public keys
- **Messages**: Encrypted messages, delivery status
- **Indexes**: Optimized for queries

## Data Flow

### Normal Flow (Internet Available)
1. User sends message → Encrypt → Send to backend
2. Backend stores message → Returns acknowledgment
3. Backend notifies recipient
4. Recipient receives → Decrypts → Displays

### Offline Flow (Bluetooth Forwarding)
1. User sends message → Encrypt → Store locally
2. Device discovers nearby devices via Bluetooth
3. Forward encrypted message with TTL
4. Receiving device checks:
   - If recipient → Decrypt and store
   - If not recipient → Check internet
     - If online → Forward to backend
     - If offline → Forward to other devices (decrement TTL)
5. When internet restored → Upload pending messages

## Security Considerations

- Messages encrypted before leaving sender device
- Intermediate devices never see plaintext
- UUID prevents duplicate processing
- TTL prevents infinite loops
- User consent required for relaying

## Loop Prevention

- Each message has TTL (Time To Live)
- Visited device IDs tracked
- Messages exceeding TTL discarded
- Devices skip messages they've already processed

