package org.hongxi.whatsmars.reactor.webflux.json;

public class Message {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Message{%s}[message=%s]", hashCode(), message);
    }
}
