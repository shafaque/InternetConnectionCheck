# Android Internet Connectivity Observer

This project demonstrates a utility class for monitoring network connectivity on Android devices using `ConnectivityManager` and Kotlin Coroutines `Flow`. The utility is implemented in a class named `AndroidConnectivityObserver`, which provides a reactive way to observe the internet connection status.

---

## Features

- **Real-time Connectivity Status**: Observe network connection changes in real-time.
- **Uses Modern Android APIs**: Implements `ConnectivityManager`'s `NetworkCallback` and Kotlin's Coroutines `Flow` for asynchronous programming.
- **Lifecycle-aware Cleanup**: Automatically unregisters network callbacks when the observation stops.

---

## Project Structure

The core implementation resides in the `AndroidConnectivityObserver` class:

- **`isConnected`**: A `Flow<Boolean>` that emits the current connectivity state (`true` for connected, `false` for disconnected).

### Key Components

1. **`ConnectivityManager`**:
   - Used to monitor network changes.
   - Registers a `NetworkCallback` to listen for connectivity updates.

2. **`NetworkCallback`**:
   - Listens for specific network events like:
     - `onCapabilitiesChanged`: Checks if the network is validated and active.
     - `onAvailable`: Indicates a new network is available.
     - `onLost`: Indicates the current network is lost.
     - `onUnavailable`: Indicates no network is available.

3. **Kotlin Coroutines `callbackFlow`**:
   - Used to emit updates to observers asynchronously.
   - Ensures the cleanup of resources (`unregisterNetworkCallback`) using `awaitClose`.

---

## License
This project is open-source. You are free to use, modify, and distribute it as per your needs.

---
