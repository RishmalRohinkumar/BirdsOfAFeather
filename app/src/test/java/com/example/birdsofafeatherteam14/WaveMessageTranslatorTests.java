package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import com.example.birdsofafeatherteam14.model.db.Student;
import com.google.android.gms.nearby.messages.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WaveMessageTranslatorTests {
    WaveMessageTranslator wmt;
    Student user;

    @Before
    public void setUp() {
        this.user = new Student(0,0,"name", "photo", "uuid", true);
        this.wmt = new WaveMessageTranslator(user);
    }

    @After
    public void cleanUp() {
        this.user = null;
        this.wmt = null;
    }

    @Test
    public void testCreateMessage() {
        Student recipient = new Student(1, 0, "recipient", "", "rec-uuid", true);
        Message msg = wmt.createMessage(recipient);
        String msgContents = new String(msg.getContent());

        assertEquals(WaveMessageTranslator.WAVE_MSG_TAG+"\n"+this.user.getUuid()+"\n"+recipient.getUuid(), msgContents);
    }

    @Test
    public void testIsValidWaveMessage() {
        Student recipient = new Student(1, 0, "recipient", "", "rec-uuid", true);
        Message msg = wmt.createMessage(recipient);
        assertEquals(true, wmt.isWaveMessage(msg));
    }

    @Test
    public void testIsInvalidWaveMessage() {
        Message msg = new Message(new String("INVALID MESSAGE").getBytes());
        assertEquals(false, wmt.isWaveMessage(msg));
    }

    @Test
    public void testInterpretMessageNotToUser() {
        String msgContent = "BOAF14-WAVE\nabcdefg\nhijklmnop";
        Message msg = new Message(msgContent.getBytes());
        assertEquals(null, wmt.interpretMessage(msg));
    }

    @Test
    public void testInterpretMessageToUser() {
        String msgContent = "BOAF14-WAVE\nabcdefg\n" + this.user.getUuid();
        Message msg = new Message(msgContent.getBytes());
        assertEquals("abcdefg", wmt.interpretMessage(msg));
    }
}
