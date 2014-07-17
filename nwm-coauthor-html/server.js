var http = require("http"), url = require("url"), path = require("path"), fs = require("fs")
port = process.argv[2] || 8888;

var proxyKeyWord = 'nwm-coauthor-webapp';
var proxyHost = 'http://localhost:8080';

var mimeTypes = {
    "html" : "text/html",
    "jpeg" : "image/jpeg",
    "jpg" : "image/jpeg",
    "png" : "image/png",
    "js" : "text/javascript",
    "css" : "text/css"
};

var serveFiles = function serveFilesFn(request, response, uri) {
    var filename = path.join(process.cwd() + "/public", uri);

    path.exists(filename, function(exists) {
        if (!exists) {
            response.writeHead(404, {
                "Content-Type" : "text/plain"
            });
            response.write("404 Not Found\n");
            response.end();
            return;
        }

        if (fs.statSync(filename).isDirectory())
            filename += '/index.html';

        fs.readFile(filename, "binary", function(err, file) {
            if (err) {
                response.writeHead(500, {
                    "Content-Type" : "text/plain"
                });
                response.write(err + "\n");
                response.end();
                return;
            }

            var mimeType = mimeTypes[path.extname(filename).split(".")[1]];
            response.writeHead(200, {'Content-Type':mimeType});
            response.write(file, "binary");
            response.end();
        });
    });
}

var proxyRequest = function(request, response, uri) {
    
}

http.createServer(function(request, response) {
    var uri = url.parse(request.url).pathname;

    if (uri.indexOf(proxyKeyWord) > -1) {
        proxyRequest(request, response, uri);
    } else {
        serveFiles(request, response, uri);
    }
    
    return;
}).listen(parseInt(port, 10));

console.log("Static file server running at\n  => http://localhost:" + port + "/\nCTRL + C to shutdown");
