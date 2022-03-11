package com.example.birdsofafeatherteam14;

public interface ExitViewUserSubject {
    void register(ExitViewUserObserver ob);
    void unregister(ExitViewUserObserver ob);
    void notifyObservers();
}
