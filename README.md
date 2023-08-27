# ShineMonitor Solar web App

## Uses javascript (main.js) backend in expressjs
## Lots of random jargon at the moment the encryption files are in .java so you know have a go at it.
### The Username and Password fields are present in the main.js file.
### With this commit the app can work totally dynamically i.e you just have to add the Username and Password and the app will login with that and get the Secret and Token for the Data Querying.
### Moreover, Rather than destroying the chart object this time we are just reading the new api call data and updating the Chart, this allows for cooler animations and overall UX and is less processor heavy.
### The deafult date is also set to the current date when the page is reloaded. If the Api call fails there is <p></p> that dispalys the error.
