# Aflamy-App
This App retrieves list of Movies in addition to some details about it.

To Run the app:
Please add the following links to your local.properties file
 - API_BASE_URL = "https://api.themoviedb.org/3/"
 - API_KEY = "PLEASE ask for it I will share it private"

Those are manily the base URL for Movies Api and the access key which is mandatory to be able to access it.

Technologies Used:
- Jetpack Compose for UI
- Paging component to manage display data in pages not full bulk, when scrolls down to the page end new bulk of data will be retrieved 
- Navigation Componet to manage app navigations
- Retrofit to retrieve data from network
- Hilt for dependency injection 

Architecture design pattern:
- Clean Architecture with MVVM

Some indirect App features:
- Support dark and light theme
- Error handling for connection issues
- Load more when get to page end.

Future Updates:
- Write some integration and end to end tests
