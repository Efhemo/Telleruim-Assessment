# Telleruim-Assessment

## Installation

Clone the repository. The App makes use of the Google Maps API, If you don’t already have an account, you will need to create one in order to get an API Key.
click [here](https://console.cloud.google.com/google/maps-apis/overview) to get started. In your project's root directory, create a file `secure.properties` and include the following lines:

```
MAPS_API_KEY=
```

## Libraries

- [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) to store and manage UI-related data in a lifecycle conscious way.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) to handle data in a lifecycle-aware fashion.
- [Navigation Component](https://developer.android.com/guide/navigation) to handle all navigations and also passing of data between destinations.
- [Material Design](https://material.io/develop/android/docs/getting-started/) dark mode supported
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) used to manage the local storage i.e. writing to and reading from the database. Coroutines help in managing background threads and reduces the need for callbacks.
- [Data Binding](https://developer.android.com/topic/libraries/data-binding/) to declaratively bind UI components in layouts to data sources.
- [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library which provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [Android KTX](https://developer.android.com/kotlin/ktx) which helps to write more concise, idiomatic Kotlin code.

## Architecture

The app leverages uni-directional data flow the in building a predictable state machine for every screen.
To achieve this, the ViewModel class of the Android Architecture Components and Kotlin couroutine were used.

The architecture of this application relies and complies with the following points below:

- A single-activity architecture, using the [Navigation Components](https://developer.android.com/guide/navigation) to manage fragment operations.
- Pattern [Model-View-ViewModel(MVVM)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) which facilitates a separation of development of the graphical user interface.
- [Android architecture components](https://developer.android.com/topic/libraries/architecture/) which help to keep the application robust, testable, and maintainable

### Modules

The App is very much modularised into different packages to ensure a clean design code all of which performs different roles in the MVVM Structure of the App. Those packages includes.

- authentication
- local
- dataSource
- UI

## Screenshots

![alt text](https://github.com/Efhemo/Telleruim-Assessment/blob/master/screenshots/dashboard_n_map.png) 
![alt text](https://github.com/Efhemo/Telleruim-Assessment/blob/master/screenshots/login_add_farm.png)
