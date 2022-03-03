package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class currentQuarterTest {


    @Test
    public void testUpdateCurrentQtr() {
        TrackCurrentQuarter test = new TrackCurrentQuarter();
        assertEquals(test.getQtr(),"WI22");
    }


}
