package com.example.birdsofafeatherteam14;

public interface IPicture {
    // Can either be a url or filepath depending on the implementation
    String getString();

    // check for valid filepath or url
    boolean isValid();
}
