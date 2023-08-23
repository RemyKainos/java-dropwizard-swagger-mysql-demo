package org.kainos.ea.client;

public class FailedToUpdateOrdersException extends Throwable {
    @Override
    public String getMessage(){
        return "Failed to update message";
    }
}
