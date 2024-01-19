# Network Demo for 2. semester
Demo to show how to use sockets to create a simple web server and client in Java.
Run tests to see how the server and client works.
- Demos:
  - Demo1 shows how to create a simple server that can answer a request from a client.
  - Demo2 shows how to create a server that can handle multiple client requests, while keeping the connection open.
  - Demo3 shows how to get meta data from the request.


- 6 simple web servers are implemented in `PicoServers.java` 
  - PicoServer01: Plain server that just answers what date it is.
  - PicoServer02: Server that can answer what date it is and prints out client info.
  - PicoServer03: Server that can answer what date it is and prints out client info and the request details
  - PicoServer04: Server that can send a file to the client based on the request path.
  - PicoServer05: Server that can handle thrown exceptions.
  - PicoServer06: Server that can return html or txt files (anything else will be handled as the name of a service)
- A Helper class: HttpRequest to unpack the request from the client.
- 3 simple web clients are implemented in `PicoClients.java`
  - PicoClient01: Client that can send a request to a server and print the response.
# repo auto created
