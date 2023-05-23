var http = require('http');
var url=require('url');

let ipport={};
http.createServer(function (req, res) {
    const queryObject = url.parse(req.url,true).query;
    res.writeHead(200, {'Content-Type': 'text/plain'});
    if(queryObject.id!==undefined&&queryObject.get===undefined) {
        ipport[queryObject.id] = queryObject.ext;
        console.log(ipport);
        res.end(ipport[queryObject.id]);
    }else if(queryObject.get!==undefined&&ipport[queryObject.get]!==undefined){
        res.end(ipport[queryObject.get]);
        console.log(ipport,"OKAY");
    }else{
        res.end("NOT FOUND");
    }
}).listen(8080);