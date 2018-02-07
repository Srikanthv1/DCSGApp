# DCSGApp

Is an Android Application developed on behalf of Dick's Sporting Goods as part of an assignment.
This is a demo application with limited functionality and is not intended for production use.

## Getting Started

The following functionality has been developed as part of this assignment.

Sorting the Store information based on the Current Location of the user and displaying the store which is nearest.
User would be able to mark one of the stores are favorite and the same will appear as the first entry.
User will also be able to view the Store photos if available, by tapping on More Details.

### Prerequisites

Minimum Android version support: Android 5.0 / API21
Android Build Tools 27.0.2
Android SDK 26

### Design

Application design follows a modular approach where various Android components have been segregated into relevant packages.
The architecture has three main components comprising Activities as part of UI layer, Data Provider as part of Model layer and
Networking for tasks related to API request and response handling.
Retrofit 2 library is used for making Web API based calls and
Picasso library is used for displaying images.

### Libraries Used
Retrofit 2 [http://square.github.io/retrofit]
Picasso [http://square.github.io/picasso]

##Testing
The following devices have been used for testing this App.
Motorola Moto G5 running Nougat 7.0
Motorola Moto C Plus running Nougat 7.0

## Deployment
Application can be deployed on an Android phone running on version 5.0 (Lollipop OS) and above.
GPS permission needs to be provided on the test device for features which make use of device current Location.
Internet should be available on the test device for making network/ Web API based calls.

### Versioning
Current application version is 1.5.

## Authors
Srikanth