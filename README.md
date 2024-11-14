## News App

This app leverages a free API from [NewsAPI.org](https://newsapi.org/) to fetch and display the
latest top news articles. The news items are presented in a scrollable list using `LazyColumn`,
providing an efficient way to handle large datasets.

The app follows the MVVM (Model-View-ViewModel) architecture, ensuring a clear separation of
concerns and easy maintainability. Data handling is managed with `StateFlow` for reactive UI
updates, while `Retrofit` is used for making network requests to the API. The ViewModel orchestrates
the data flow between the UI and the data layer, offering a robust and scalable solution for
displaying real-time news updates.

Each user should have a unique API key. To create one, log in at https://newsapi.org/ and add it to
the NewsServiceAPI_KEY field to enable network req
