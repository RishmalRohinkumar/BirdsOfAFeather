package com.example.birdsofafeatherteam14;

import android.util.Patterns;

public class URLPicture implements IPicture {
    private String url;

    URLPicture(String url) {
        this.url = url;
    }

    @Override
    public String getString() {
        return url;
    }

    @Override
    public boolean isValid() {
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
