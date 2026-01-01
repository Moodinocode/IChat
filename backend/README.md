# Backend Server

Node.js/Express backend for the Secure Messaging application.

## Setup

1. Install dependencies:
```bash
npm install
```

2. Create a `.env` file in the `backend/` directory:
```
PORT=3000
NODE_ENV=development
MONGODB_URI=mongodb://localhost:27017/secure-messaging
JWT_SECRET=your-secret-key-change-in-production
```

3. Make sure MongoDB is running (local or cloud instance)

4. Start the server:
```bash
npm start
```

For development with auto-reload:
```bash
npm run dev
```

## API Endpoints

### Health Check
- `GET /health` - Server health status

### Users
- `POST /api/users/register` - Register a new user
- `GET /api/users/:userId` - Get user by ID

### Messages
- `POST /api/messages/send` - Send a message
- `GET /api/messages/pending/:recipientId` - Get pending messages
- `PATCH /api/messages/delivered/:messageId` - Mark message as delivered
- `PATCH /api/messages/read/:messageId` - Mark message as read

## Architecture

- **Models**: MongoDB schemas (User, Message)
- **Routes**: API endpoint handlers
- **Server**: Express app configuration

## Features

- Message deduplication using UUID
- Exactly-once delivery semantics
- MongoDB indexing for performance
- Clean, modular code structure

