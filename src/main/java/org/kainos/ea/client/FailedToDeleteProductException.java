package org.kainos.ea.client;

public class FailedToDeleteProductException extends Exception {
    @Override
    public String getMessage(){
        return "Failed to delete product";
    }
}
