# Core Module - Explore the Globe

This is the core business logic module for the Explore the Globe application, built using Kotlin Multiplatform to support Android, iOS, and JVM platforms.

## Overview

The core module is responsible for fetching, caching, and providing country data to the application. It is designed to be platform-independent and follows clean architecture principles.

## Features

- Kotlin Multiplatform support (Android, iOS, JVM)
- Clean Architecture implementation
- Offline-first data strategy
- Comprehensive unit test coverage

## Architecture

### Data Layer
- **Remote Data Source**: Uses Ktor client to fetch country data from REST API
- **Local Data Source**: SQLDelight for cross-platform data persistence
- **Repository**: Implements offline-first strategy with remote data refresh

### Domain Layer
- **Models**: Platform-independent data models
- **Repository Interfaces**: Define data access contracts
- **Use Cases**: Encapsulate business logic

## Key Components

### CountryApi
Handles remote data fetching using Ktor, with:
- Error handling
- JSON serialization
- HTTP client configuration

### CountryDao
SQLDelight-based data access with:
- Country data persistence
- Flow-based reactive queries
- CRUD operations

### CountryRepository
Implements offline-first strategy:
- Caches remote data locally
- Provides data access to domain layer
- Handles data refresh logic

### GetCountriesUseCase
Simple domain layer use case that:
- Abstracts repository access
- Provides clean API for UI layer

## Dependencies

- **Ktor**: Network requests
- **SQLDelight**: Cross-platform persistence
- **Kotlinx.Serialization**: JSON parsing
- **Coroutines**: Async operations
- **Koin**: Dependency injection

## Testing

Comprehensive unit tests cover:
- Repository implementation
- API client
- Database operations
- Use case logic

## Usage

The module is designed to be consumed by platform-specific UI implementations:

```kotlin
class SampleViewModel(
    private val getCountriesUseCase: GetCountriesUseCase
) {
    fun loadCountries() {
        getCountriesUseCase()
            .onSuccess { countries -> 
                // Handle countries
            }
    }
}
```
