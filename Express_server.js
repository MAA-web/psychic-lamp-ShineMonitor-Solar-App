const express = require('express');
const cors = require('cors');

const app = express();
const port = 3000; // Choose a port

app.use(cors());
app.use(express.static('C:/Users/Ahmed/projects/SolarApp/data/')); // Replace 'your_directory' with the directory containing your files.

app.listen(port, () => {
  console.log(`Server is running at http://localhost:${port}`);
});
