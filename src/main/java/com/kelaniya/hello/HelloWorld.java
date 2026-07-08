package com.kelaniya.hello;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * This is the SERVICE INTERFACE (also called the SEI - Service Endpoint Interface).
 *
 * In terms of your lecture notes (Section 3 - Remote Procedure Calls):
 * "The service interface specifies the procedures offered by a server and
 *  defines their argument types."
 *
 * @WebService marks this as a remotely callable contract.
 * @SOAPBinding(style = Style.RPC) tells JAX-WS to use RPC-style encoding,
 * where the SOAP message body literally mirrors a procedure call:
 * methodName(arguments) -> the operationId + arguments described in your notes.
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface HelloWorld {

    @WebMethod
    String sayHello(String name);

    @WebMethod
    int add(int a, int b);
}
