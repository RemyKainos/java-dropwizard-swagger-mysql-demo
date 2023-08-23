package org.kainos.ea.client;

public class OrderDoesNotExistException extends Exception {
    @Override
    public String getMessage(){
        return "Order does not exist.";
    }
}
