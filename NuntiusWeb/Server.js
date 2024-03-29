var net = require('net');

function start() {

	var HOST = '0.0.0.0';
	var PORT = 8888;

	net.createServer(function(sock) {
	    
	    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);
	    
	    sock.on('data', function(data) {
	        
	        console.log('DATA ' + sock.remoteAddress + ': ' + data);
	        sock.write('ANSWER: You said "' + data + '"'); 
	    });
	    
	    sock.on('close', function(data) {
	        console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
	    });
	}).listen(PORT, HOST);

	console.log('Server listening on ' + HOST +':'+ PORT);
}

exports.start = start;