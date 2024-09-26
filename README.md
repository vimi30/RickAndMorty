Rick and Morty Multiplatform App

A Kotlin Multiplatform application for Android, iOS, and Desktop platforms that provides an interactive experience with characters and episodes from the Rick and Morty TV show. Built using Compose Multiplatform, the app showcases the power of sharing code across different platforms while delivering a native user experience.

Table of Contents

	•	Features
	•	Screenshots
	•	Architecture
	•	Technologies Used
	•	Getting Started
	•	Prerequisites
	•	Installation
	•	Usage
	•	Contributing
	•	License
	•	Acknowledgments

 Features

	•	Multiplatform Support: Runs on Android, iOS, and Desktop platforms with shared business logic.
	•	Home Screen: Displays all characters from the Rick and Morty show.
	•	Episodes Screen: Shows a list of all episodes.
	•	Search Functionality: Allows users to search for characters.
	•	Character Details: View detailed information about each character.
	•	Episode Details: View detailed information about each episode.
	•	Responsive UI: Adapts to different screen sizes and orientations.

Demo

Android 

https://github.com/user-attachments/assets/99092426-c219-4dcf-bb24-a987a2bfd968

Iphone

https://github.com/user-attachments/assets/085a3a60-96da-4739-af03-9026822c60c4

Desktop

https://github.com/user-attachments/assets/2c4e69c5-84d7-43ae-8151-49ee1cbc8775


Architecture

The project follows the Model-View-ViewModel (MVVM) architectural pattern, which promotes a clean separation of concerns and facilitates testability.

	•	Model: Represents the data layer, including network responses and data models.
	•	View: UI components built with Compose Multiplatform.
	•	ViewModel: Handles the presentation logic and state management.

Technologies Used

	•	Kotlin Multiplatform: Enables code sharing between Android, iOS, and Desktop platforms.
	•	Compose Multiplatform: Declarative UI framework for building native user interfaces.
	•	Ktor: Asynchronous framework for making network calls.
	•	Koin: Lightweight dependency injection framework.
	•	Coil Multiplatform: Image loading library for Kotlin Multiplatform projects.
	•	Coroutines: For asynchronous programming.
	•	JetBrains Kotlin Serialization: For parsing JSON data.

Getting Started

Prerequisites

	•	JDK 11 or higher
	•	Android Studio Flamingo | 2022.2.1 or higher with Kotlin Multiplatform Mobile plugin installed.
	•	Xcode 13 or higher (for iOS development).
	•	Gradle 7.0 or higher

Installation

1.	Clone the Repository

  	    git clone https://github.com/yourusername/RickAndMorty.git
        cd RickAndMorty

3.	Open the Project
   
	    •	Android Studio: Open the project from the cloned directory.
	    •	Xcode: Open iosApp/iosApp.xcworkspace.

4.	Sync and Build
   
	    •	Allow Gradle to sync and download all dependencies.
	    •	Build the project using the IDE’s build tools.

Usage

Running on Android

	1.	Connect an Android device or start an Android emulator.
	2.	Run the app from Android Studio by selecting the Android run configuration.

Running on iOS

	1.	Open the iOS project in Xcode (iosApp/iosApp.xcworkspace).
	2.	Select a simulator or connect an iOS device.
	3.	Run the app from Xcode.

Running on Desktop

	1.	Run the desktop application using the desktop run configuration in your IDE.
	2.	The desktop application will launch in a new window.

Contributing

Contributions are welcome! Please follow these steps:

	1.	Fork the repository.
	2.	Create a new branch for your feature or bug fix.
	3.	Commit your changes with clear messages.
	4.	Push to your fork and submit a pull request.

Please ensure your code adheres to the project’s coding conventions and standards.

License

This project is licensed under the MIT License.

Acknowledgments

	•	JetBrains for Kotlin Multiplatform and Compose Multiplatform.
	•	Rick and Morty API for providing the data.
	•	Coil-kt for the image loading library.
	•	InsertKoin for the dependency injection framework.
	•	Ktor for the networking framework.

