package com.example.birdsofafeatherteam14;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MockMessageListenerClass extends MessageListener{
    private final MessageListener messageListener;

    public MockMessageListenerClass(MessageListener realMessageListener, List<String> messageStrs) {
        this.messageListener = realMessageListener;

        for (String msg : messageStrs) {
            Message message = new Message(msg.getBytes(StandardCharsets.UTF_8));
            this.messageListener.onFound(message);
            this.messageListener.onLost(message);
        }
    }
}
