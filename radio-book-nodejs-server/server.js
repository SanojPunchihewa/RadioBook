// Load required modules
var http = require("http");                  // http server core module
var https = require('https');
var express = require("express");               // web framework external module
var serveStatic = require('serve-static');      // serve static files
var cors = require('cors');
var bodyParser = require('body-parser');
var path = require('path');
const fs = require('fs');
var axios = require('axios');

const route = require('./api/routes/sftpRoutes');

const serverPath = "http://localhost:8080/";

// Set process name
process.title = "node-radio-book";

// Setup and configure Express http server. Expect a subfolder called "static" to be the web root.
var app = express();
app.use(cors())
app.use(bodyParser.urlencoded({extended: true}))
app.use(bodyParser.json());

app.use('/api', route);

app.use(express.static(path.join(__dirname, '/public')));

var paths = ['/'];

app.get(paths, (req, res, next) => {    
    res.sendFile(__dirname + '/public/index.html')
})

var port = process.env.PORT || 8080;
// Start Express http server on port 8080
var webServer = http.createServer(app);

//listen on port 8080
webServer.listen(port, function () {
    console.log('Server started at PORT:'+ port);
});
