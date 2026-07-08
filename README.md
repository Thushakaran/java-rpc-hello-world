# Java RPC HelloWorld (JAX-WS, RPC style) — Step by Step

This project is a complete, minimal JAX-WS RPC-style HelloWorld system:
a server that publishes a remote service, and a client that calls it
over the network as if it were a local method.

## 0. What you're building (in one picture)

```
 Client (HelloWorldClient.java)                 Server (Publisher.java)
 --------------------------------                --------------------------------
 client stub / proxy  ---- Request (SOAP/XML)---> dispatcher -> HelloWorldImpl
                      <---- Reply (SOAP/XML) -----   (runs the real method)
```

This maps directly onto Lecture 05:
- **Publisher.java** starts the server and exposes the **service interface** (WSDL).
- **HelloWorld.java** is the **service interface** (SEI) — defines operations + argument types.
- **HelloWorldImpl.java** is the **service procedure** — the real logic.
- **HelloWorldClient.java** builds a **client stub** at runtime and calls the remote method.
- Underneath, JAX-WS/HTTP plays the role of the **Request–Reply protocol** from Section 1
  (client blocks until reply arrives), and the **dispatcher** from Section 3 routes the
  incoming request to the right method by operation name.

## 1. Install prerequisites (one-time setup)

You need:
1. **JDK 17** (or JDK 11/21 — all work with this project). Download from
   https://adoptium.net (choose "Temurin", your OS, and LTS version).
2. **Maven** (build tool). Download from https://maven.apache.org/download.cgi
   or install via a package manager:
   - Windows (with Chocolatey): `choco install maven`
   - Mac: `brew install maven`
   - Ubuntu/WSL: `sudo apt install maven`

Verify both are installed by opening a terminal / Command Prompt and running:
```
java -version
mvn -version
```
You should see version numbers printed, not "command not found".

## 2. Open the project

Unzip/copy this `java-rpc-helloworld` folder anywhere on your machine. Open it in
IntelliJ IDEA (File → Open → select the folder with `pom.xml`) — IntelliJ will
detect it's a Maven project and download dependencies automatically. (You already
use IntelliJ per your usual setup, so this should feel familiar.)

If you'd rather use the terminal instead of IntelliJ, that works too — just `cd`
into the folder before running the commands below.

## 3. Start the server

In IntelliJ: right-click `Publisher.java` → **Run 'Publisher.main()'**.

Or from the terminal:
```
mvn compile exec:java -Dexec.mainClass="com.kelaniya.hello.Publisher"
```

You should see:
```
HelloWorld RPC service published at: http://localhost:9999/hello
WSDL available at: http://localhost:9999/hello?wsdl
Server is running. Press Ctrl+C to stop.
```

**Leave this running.** Open `http://localhost:9999/hello?wsdl` in your browser —
this XML document is the machine-readable **service interface description**.
Take a screenshot of this for your article; point out the `<operation name="sayHello">`
and `<operation name="add">` elements — these are your `operationId`s from Figure 1.2.

## 4. Run the client (in a NEW terminal/run, while the server keeps running)

In IntelliJ: right-click `HelloWorldClient.java` → **Run 'HelloWorldClient.main()'**.

Or from the terminal (new terminal window, server still running in the first one):
```
mvn compile exec:java -Dexec.mainClass="com.kelaniya.client.HelloWorldClient"
```

Expected output:
```
[Client] Server replied: Hello, Thushakaran! This message was returned via Java RPC (JAX-WS).
[Client] Server replied with sum: 42
```

And in the SERVER terminal you should simultaneously see:
```
[Server] sayHello() invoked remotely with name = Thushakaran
[Server] add() invoked remotely with a=15, b=27
```

Screenshot both terminals side by side — this is your best evidence for the article:
it visually proves a "local-looking" method call actually triggered network
communication to a separate process.

## 5. (Optional, more advanced) Generate a static client stub with wsimport

The client above uses a **dynamic proxy** (`Service.create(...).getPort(...)`),
which is simpler to set up. If you want to also show the classic **wsimport**
workflow (mentioned in your lecture notes as the "interface compiler" that
auto-generates stubs), while the server is running:

```
wsimport -keep -p com.kelaniya.client.stub http://localhost:9999/hello?wsdl
```

If `wsimport` isn't available (removed from JDK 11+), uncomment the
`jaxws-maven-plugin` block in `pom.xml` and run `mvn generate-sources` instead —
it does the same thing automatically. This generates real `.java` source files
(a generated proxy class + service class) you can open and show in your article
as literal proof of "the stub is auto-generated."

## 6. Capture evidence for your article

Take screenshots of:
- The WSDL in your browser (step 3).
- Server terminal output + client terminal output side-by-side (step 4).
- (Optional) The generated stub source file from step 5, if you did it.
- Your project file structure in IntelliJ.

## 7. Push to GitHub

```
cd java-rpc-helloworld
git init
git add .
git commit -m "Java RPC HelloWorld using JAX-WS (RPC style)"
```
Create a new empty repository on GitHub (no README/license, since you already
have files), then:
```
git remote add origin https://github.com/<your-username>/java-rpc-helloworld.git
git branch -M main
git push -u origin main
```

## 8. Write and publish the Medium article

See `medium-article-outline.md` in this folder — it gives you a section-by-section
outline with the concepts you need to explain and where your screenshots go. Write
the actual prose yourself, in your own words, using what you observed in steps 3–4 —
this is what the plagiarism check is looking for.

## 9. Submit

Submit both links via LMS:
- Medium article URL
- GitHub repository URL

## Troubleshooting

| Problem | Fix |
|---|---|
| `Address already in use` when starting Publisher | Another process is using port 9999. Change `9999` to another port (e.g. `9998`) in both `Publisher.java` and `HelloWorldClient.java`. |
| Client can't find service / `WSDLException` | Make sure the server (Publisher) is still running before you start the client. |
| Maven can't download dependencies | Check your internet connection; corporate/university Wi-Fi sometimes blocks Maven Central — try mobile hotspot once to fetch dependencies, then they're cached locally. |
| IntelliJ doesn't recognize it as Maven project | Right-click `pom.xml` → Add as Maven Project. |
