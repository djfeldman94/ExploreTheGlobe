# Explore The Globe - Android App

A clean, accessible Android application that displays country information from around the world. Built with modern Android development practices and a focus on user experience.

## Overview

The app module provides the Android-specific UI implementation for the Explore The Globe application. It consumes the core module's domain layer through the `GetCountriesUseCase` to display country data in a simple, accessible list format.

## Architecture

### MVVM Implementation

- **ViewModel**: `CountriesViewModel` manages UI state using Kotlin Flow
- **View**: `MainActivity` observes state changes and updates the UI
- **Model**: Provided by the core module's domain layer

### Key Components

```kotlin
// State handling in ViewModel
sealed class CountriesState {
    data object Loading : CountriesState()
    data class Success(val countries: List<Country>) : CountriesState()
    data class Error(val message: String) : CountriesState()
}
```

### Dependency Injection

Uses Koin for dependency injection with a simple setup:

```kotlin
val appModule = module {
    viewModel { CountriesViewModel(get()) }
    single<CoroutineContext> { Dispatchers.Default + SupervisorJob() }
    single<CoroutineScope> { CoroutineScope(get()) }
    includes(coreModule)
}
```

## UI Features

### Accessibility

- TalkBack support with meaningful content descriptions
- Proper focus navigation order
- High contrast text in both light and dark themes
- Clear error states with descriptive messages

### User Experience

- Pull-to-refresh functionality
- Loading state indicators
- Error handling with user feedback
- Smooth list scrolling with RecyclerView

### Views

The app uses traditional XML layouts with:
- SwipeRefreshLayout for refresh gestures
- RecyclerView with LinearLayoutManager
- ProgressBar for loading states
- TextView for error messages

## State Management

The app handles three main states:
1. **Loading**: Shows a progress indicator
2. **Success**: Displays the list of countries
3. **Error**: Shows error message with retry option

## Theme Support

Implements both light and dark themes using:
- Material Design color system
- Theme-aware components
- System theme integration

## Getting Started

1. Build the core module first
2. Run the app module
3. The app will automatically fetch and display countries

## Dependencies

- Core module (local) - Provides domain layer and data handling
- AndroidX libraries - UI components and lifecycle management
- Koin - Dependency injection
- Kotlin Coroutines - Asynchronous operations
- Material Design - UI components and theming

## Testing

The app module focuses on UI testing with:
- Unit tests for ViewModel logic
- UI tests for component interaction
- Integration tests with core module

## Future Improvements

- Implement search functionality
- Add country details view
- Support for different list layouts
- Animated transitions
- Offline mode indicator
