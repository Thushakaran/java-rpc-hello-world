package com.kelaniya.client;

import com.kelaniya.hello.HelloWorld;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * This is the CLIENT.
 *
 * Lecture notes, Section 3 (Implementation):
 *   "Client stub: marshals procedure ID and arguments; sends; unmarshals results."
 *
 * Service.create(...) + service.getPort(HelloWorld.class) asks JAX-WS to build
 * a CLIENT STUB (also called a "proxy") at runtime. This proxy object:
 *   1. Implements the same HelloWorld interface as the server.
 *   2. When you call proxy.sayHello("Thushakaran"), it does NOT run any real
 *      logic locally. Instead it:
 *        a. Marshals (encodes) the method name + arguments into a SOAP/XML
 *           request message (this corresponds to requestId, remoteReference,
 *           operationId, arguments from your Figure 1.2/1.2 message structure).
 *        b. Sends that request over HTTP to the server (doOperation, Figure 2.2).
 *        c. Blocks and waits (this is the synchronous Request-Reply protocol
 *           described in Section 1 - the client blocks until the reply arrives).
 *        d. Unmarshals (decodes) the SOAP reply back into a normal Java String/int.
 *
 * From the programmer's point of view, calling a remote method looks
 * EXACTLY like calling a local method - that's the whole point of RPC:
 * it hides the network communication, encoding, and call semantics.
 */
public class HelloWorldClient {
    public static void main(String[] args) throws Exception {
        URL wsdlUrl = new URL("http://localhost:9999/hello?wsdl");

        // The service name matches what JAX-WS derives from HelloWorldImpl:
        // {namespace}ServiceName - printed by the server when it starts up,
        // and visible if you open the WSDL URL in a browser.
        QName serviceName = new QName("http://hello.kelaniya.com/", "HelloWorldImplService");

        Service service = Service.create(wsdlUrl, serviceName);
        HelloWorld helloWorld = service.getPort(HelloWorld.class);

        // These look like ordinary local method calls, but each one triggers
        // a full request-reply round trip over the network (RPC transparency).
        String greeting = helloWorld.sayHello("Thushakaran");
        System.out.println("[Client] Server replied: " + greeting);

        int sum = helloWorld.add(15, 27);
        System.out.println("[Client] Server replied with sum: " + sum);
    }
}
