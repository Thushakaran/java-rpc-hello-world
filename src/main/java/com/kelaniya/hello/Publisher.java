package com.kelaniya.hello;

import javax.xml.ws.Endpoint;

/**
 * This class starts the server and "publishes" the HelloWorld service so
 * remote clients can find and call it.
 *
 * Endpoint.publish() does three things behind the scenes, matching your
 * lecture notes' Request-Reply architecture (Figure 2.2 / Figure 3.1):
 *   1. Opens a lightweight HTTP server on the given URL and port.
 *   2. Generates the WSDL (Web Services Description Language document) -
 *      this is the "service interface" description a client reads to know
 *      what operations exist and what arguments they take.
 *   3. Wires up the dispatcher: incoming requests are routed by operationId
 *      (here, the SOAP <soapAction> / method name) to HelloWorldImpl.
 *
 * Run this class FIRST, then leave it running, then run HelloWorldClient
 * in a separate terminal.
 */
public class Publisher {
    public static void main(String[] args) {
        String address = "http://localhost:9999/hello";
        Endpoint.publish(address, new HelloWorldImpl());
        System.out.println("HelloWorld RPC service published at: " + address);
        System.out.println("WSDL available at: " + address + "?wsdl");
        System.out.println("Server is running. Press Ctrl+C to stop.");
    }
}
