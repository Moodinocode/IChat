# Getting Started Guide

## Prerequisites

Before starting, ensure you have:

1. **Node.js** (v18 or higher)
   - Download from [nodejs.org](https://nodejs.org/)

2. **MongoDB** 
   - Local installation or MongoDB Atlas account
   - Download from [mongodb.com](https://www.mongodb.com/try/download/community)

3. **Android Studio**
   - Download from [developer.android.com](https://developer.android.com/studio)
   - Includes Android SDK and emulator

4. **JDK 11+**
   - Usually included with Android Studio

## Initial Setup

### 1. Backend Setup

```bash
# Navigate to backend directory
cd backend

# Install dependencies
npm install

# Create .env file (copy from .env.example or create manually)
# Add your MongoDB connection string
echo "PORT=3000" > .env
echo "MONGODB_URI=mongodb://localhost:27017/secure-messaging" >> .env
echo "NODE_ENV=development" >> .env

# Start MongoDB (if running locally)
# Windows: net start MongoDB
# Mac/Linux: mongod

# Start the server
npm start
```

The backend should now be running on `http://localhost:3000`

### 2. Mobile App Setup

1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the `mobile/` directory
4. Wait for Gradle sync to complete
5. Connect an Android device or start an emulator
6. Click "Run" to build and install the app

### 3. Testing the Setup

#### Backend Health Check
```bash
curl http://localhost:3000/health
```

Expected response:
```json
{"status":"ok","message":"Server is running"}
```

#### Test User Registration
```bash
curl -X POST http://localhost:3000/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "deviceId": "device456",
    "username": "TestUser",
    "publicKey": "test-public-key"
  }'
```

## Next Steps

1. **Implement Chat UI** - Create the messaging interface
2. **Bluetooth Module** - Implement device discovery and forwarding
3. **Encryption Module** - Add end-to-end encryption
4. **Offline Storage** - Complete Room database integration
5. **Network Detection** - Implement connectivity monitoring
6. **Message Forwarding Logic** - Complete Bluetooth forwarding with TTL

## Development Workflow

1. Start MongoDB
2. Start backend server (`npm start` in `backend/`)
3. Run mobile app from Android Studio
4. Test features incrementally

## Troubleshooting

### Backend Issues
- **MongoDB connection error**: Ensure MongoDB is running
- **Port already in use**: Change PORT in `.env` file

### Android Issues
- **Gradle sync failed**: Check internet connection, invalidate caches
- **Build errors**: Ensure Android SDK is properly installed
- **Bluetooth permissions**: Grant all required permissions in device settings

## Project Structure Reference

See `docs/ARCHITECTURE.md` for detailed architecture information.

