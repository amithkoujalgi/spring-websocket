## Spring-based WebSocket Chat Client

A sample client implementation of Spring WebSocket. 

The server includes a web-based client (HTML/JS), however this client demonstrates usage of WebSocket in the Spring context.

Build:

```bash
mvn clean install
```

Run the client while the server is running:

```bash
java -jar target/chat-client-0.1.0.jar
```

The client connects to the server, sends
a starting message and shows a prompt on the console which can be used
to send further messages. Any messages sent to the server (maybe from
a second console or the web client) is also received and echoed on the
console.
