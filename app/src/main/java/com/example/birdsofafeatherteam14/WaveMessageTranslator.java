package com.example.birdsofafeatherteam14;

import android.util.Log;

import com.example.birdsofafeatherteam14.model.db.Student;
import com.google.android.gms.nearby.messages.Message;

// This class deals with translating Messages into Waves and vice-versa
public class WaveMessageTranslator {
    public static String WAVE_MSG_TAG = "BOAF14-WAVE";

    private Student user;
    WaveMessageTranslator(Student user) {
        this.user = user;
    }

    // Create a wave message in the correct format to be sent out on nearby
    public Message createMessage(Student recipient) {
        System.out.println("Creating Wave from " + this.user.getUuid() + " to " + recipient.getUuid());
        String s = WAVE_MSG_TAG + "\n" + this.user.getUuid() + "\n" + recipient.getUuid();
        return new Message(s.getBytes());
    }

    // Determines if the message is in the wave format
    public boolean isWaveMessage(Message message) {
        String msgContent = new String(message.getContent());
        String[] s = msgContent.split("\n");
        return (s[0].equals(WAVE_MSG_TAG));
    }

    // Interpret an incoming message that we already know is in the wave format
    // Returns the uuid of the person that waved at the user if they are waving at us
    // returns null otherwise
    public String interpretMessage(Message message) {
        String msgContent = new String(message.getContent());
        System.out.println("Interpreting Message for wave content: " + msgContent);

        String[] m = msgContent.split("\n");
        try {
            if (m[2].equals(this.user.getUuid())) {
                // Return the sender's UUID because they are waving at us
                return m[1];
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Invalid Message Format: Doing nothing");
        }
        return null;
    }

}
