## Demonstration of a simple Spring-based chat server and client.

Build the project:

```bash
mvn clean install
```

Run the server:

```bash
java -jar chat-server/target/chat-server-0.1.0.jar
```

Starts the webserver at 8080.
Now, head to [http://localhost:8080/](http://localhost:8080/) in your browser.

Run the client while the server is running:

```bash
java -jar chat-client/target/chat-client-0.1.0.jar
```
