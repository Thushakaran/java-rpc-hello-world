package com.kelaniya.hello;

import javax.jws.WebService;

/**
 * This is the ACTUAL SERVICE PROCEDURE (the real logic that runs on the server).
 *
 * Lecture notes, Section 3 (Implementation):
 *   "Server stub: unmarshals arguments; calls service procedure; marshals return values."
 *
 * You never call this class directly from the client. The client only ever
 * sees the HelloWorld interface. JAX-WS auto-generates a "server stub" +
 * "dispatcher" behind the scenes that receives the incoming SOAP request,
 * unmarshals (decodes) the arguments, calls THIS class's method, then
 * marshals (encodes) the return value back into a SOAP reply.
 */
@WebService(endpointInterface = "com.kelaniya.hello.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String sayHello(String name) {
        System.out.println("[Server] sayHello() invoked remotely with name = " + name);
        return "Hello, " + name + "! This message was returned via Java RPC (JAX-WS).";
    }

    @Override
    public int add(int a, int b) {
        System.out.println("[Server] add() invoked remotely with a=" + a + ", b=" + b);
        return a + b;
    }
}
