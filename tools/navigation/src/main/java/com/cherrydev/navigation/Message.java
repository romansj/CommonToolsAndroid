package com.cherrydev.navigation;

public class Message<T> {
    T content;

    public Message(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
