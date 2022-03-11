package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;

public class currentQuarterTest {


    @Test
    public void testUpdateCurrentQtrWI_22() {
        CurrentQuarterTracker test = new CurrentQuarterTracker(LocalDate.of(2022, 3, 3));
        assertEquals(test.getQtr(),"WI");
        assertEquals(test.getYr(),"2022");
    }

    @Test
    public void testUpdateCurrentQtrFA_2019() {
        CurrentQuarterTracker test = new CurrentQuarterTracker(LocalDate.of(2019, 10, 3));
        assertEquals(test.getQtr(),"FA");
        assertEquals(test.getYr(),"2019");
    }

    @Test
    public void testUpdateCurrentQtrSP_2030() {
        CurrentQuarterTracker test = new CurrentQuarterTracker(LocalDate.of(2030, 4, 3));
        assertEquals(test.getQtr(),"SP");
        assertEquals(test.getYr(),"2030");
    }

    @Test
    public void testUpdateCurrentQtrSS1_2022() {
        CurrentQuarterTracker test = new CurrentQuarterTracker(LocalDate.of(2022, 7, 3));
        assertEquals(test.getQtr(),"SS1");
        assertEquals(test.getYr(),"2022");
    }
    @Test
    public void testUpdateCurrentQtrSS2_2022() {
        CurrentQuarterTracker test = new CurrentQuarterTracker(LocalDate.of(2022, 8, 3));
        assertEquals(test.getQtr(),"SS2");
        assertEquals(test.getYr(),"2022");
    }
}
