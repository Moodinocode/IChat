# 1. Introduction

## 1.1 Purpose
This document defines the **functional** and **technical** requirements for a capstone project involving an AI-assisted, offline-capable, secure mobile messaging application. The goal is to guide both human developers and AI agents in implementing the system in a clean, scalable, and secure manner.

## 1.2 Scope
The application is a **mobile chat app (similar to WhatsApp)** that supports:
- Internet-based messaging
- Bluetooth-based message forwarding when internet is unavailable
- Secure, encrypted communication
- Temporary offline storage with guaranteed delivery
- User consent for data relaying

The system consists of:
- A **mobile frontend** (Android – Java/Kotlin)
- A **backend server** (Node.js)
- A **database** (MongoDB)

---

# 2. System Overview

## 2.1 High-Level Architecture
- **Mobile Client**
  - Chat UI
  - Bluetooth communication module
  - Encryption & decryption module
  - Offline message storage
  - Consent & settings management

- **Backend Server**
  - User management
  - Message persistence
  - Message deduplication
  - Delivery acknowledgments

- **Database (MongoDB)**
  - Users
  - Messages
  - Delivery states

---

# 3. Functional Requirements

## 3.1 Chat Application Core
- The app SHALL provide real-time chat functionality similar to WhatsApp.
- Users SHALL be able to send and receive text messages.
- Each message SHALL have a unique identifier (UUID).

---

## 3.2 Hybrid Connectivity (Wi-Fi / Mobile Data / Bluetooth)

### 3.2.1 Internet Messaging
- When Wi-Fi or mobile data is available, messages SHALL be sent directly to the backend server.

### 3.2.2 Bluetooth-Based Message Forwarding
- When internet is unavailable, the app SHALL attempt to send messages via Bluetooth to nearby users.
- A receiving device SHALL:
  - Check if it is the intended recipient.
  - If not the recipient:
    - Check if it has internet access.
    - If internet is available, forward the message to the server.
    - If not, forward the message to other nearby devices via Bluetooth.

---

## 3.3 Poisoning / Loop Prevention Mechanism
- The system SHALL prevent infinite Bluetooth message hopping.
- Each message SHALL include:
  - A hop count (TTL – Time To Live)
  - A list or hash of previously visited device IDs
- Messages exceeding the hop limit SHALL be discarded.
- Devices SHALL NOT forward a message they have already processed.

---

## 3.4 Temporary Offline Storage
- Each device SHALL maintain temporary encrypted storage for messages received via Bluetooth.
- Stored messages SHALL:
  - Persist across app restarts
  - Be uploaded to the backend once internet access is restored
- After successful upload, messages SHALL be safely deleted from local storage.

---

## 3.5 Data Integrity (No Duplicates, No Loss)
- The system SHALL guarantee **exactly-once delivery semantics**.
- Duplicate messages SHALL be prevented using:
  - Message UUIDs
  - Server-side deduplication
- Messages SHALL NOT be lost during:
  - Bluetooth forwarding
  - Offline storage
  - Network transitions

---

## 3.6 Secure Communication
- End-to-end encryption SHALL be implemented.
- Messages SHALL be encrypted on the sender’s device.
- Only the intended recipient SHALL be able to decrypt the message.
- Intermediate Bluetooth forwarding devices SHALL:
  - Never have access to plaintext content
  - Only forward encrypted payloads

---

## 3.7 User Consent & Data Relay Control
- The app SHALL request explicit user consent before:
  - Using a device’s Wi-Fi or mobile data to forward others’ messages
- A settings toggle SHALL be provided to:
  - Enable or disable message relaying
- If consent is disabled, the device SHALL NOT forward messages for others.

---

# 4. Non-Functional Requirements

## 4.1 Code Quality
- Clean code is the **highest priority**.
- Code SHALL be:
  - Readable
  - Modular
  - Well-documented
  - Testable

---

## 4.2 Performance
- Bluetooth message forwarding SHALL be lightweight.
- Battery and data usage SHALL be minimized.

---

## 4.3 Scalability
- Backend SHALL support horizontal scaling.
- MongoDB collections SHALL be indexed appropriately.

---

## 4.4 Reliability
- The system SHALL tolerate intermittent connectivity.
- Message delivery SHALL eventually succeed once connectivity is restored.

---

# 5. Technical Requirements

## 5.1 Technology Stack

### Mobile Frontend
- Language: **Java or Kotlin**
- Platform: **Android**

### Backend
- Runtime: **Node.js**
- Framework: Express.js (or equivalent)

### Database
- **MongoDB** for message and user storage

---

## 5.2 Coding Principles (High Priority)
The project SHALL strictly follow established programming principles, including but not limited to:

1. **DRY (Don’t Repeat Yourself)**
2. **KISS (Keep It Simple, Stupid)**
3. **YAGNI (You Aren’t Gonna Need It)**
4. **Separation of Concerns**
5. **Single Responsibility Principle**
6. **Modularity and Reusability**
7. **Meaningful Naming and Consistency**

(As referenced from: GeeksForGeeks – Common Programming Principles)

---

# 6. AI Agent Responsibilities

The AI agent assisting this project SHALL:
- Adhere strictly to this requirements document
- Prioritize clean, maintainable solutions
- Avoid unnecessary complexity
- Enforce security and data integrity guarantees
- Respect user consent at all times

---

# 7. Assumptions & Constraints

- Bluetooth range is limited and unreliable by nature
- Users may frequently switch between offline and online states
- Privacy and security are critical and non-negotiable

---

# 8. Success Criteria

The project SHALL be considered successful if:
- Messages are delivered securely with no duplicates or loss
- Offline Bluetooth forwarding works reliably
- User consent is respected
- Code quality meets clean code standards

---

**End of Document**

