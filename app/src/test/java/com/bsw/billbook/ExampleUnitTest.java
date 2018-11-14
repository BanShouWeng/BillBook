package com.bsw.billbook;

import org.junit.Test;

import java.net.PortUnreachableException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void timeTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1532327813000L);
        System.out.println((calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
    }

    @Test
    public void uuid(){
        System.out.print(UUID.randomUUID().toString());
    }

    @Test
    public void split(){
        String[] recordTimeSplit= "2018/15/22".split("/");
        System.out.println(Arrays.toString(recordTimeSplit));
    }
}