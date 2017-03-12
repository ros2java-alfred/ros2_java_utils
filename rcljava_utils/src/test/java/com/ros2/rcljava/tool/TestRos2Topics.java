package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.ros2.rcljava.tool.Ros2Topics;

public class TestRos2Topics {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested


    @Test
    public final void testEmpty() throws InterruptedException {
        Ros2Topics.main(new String[]{});
        assertEquals(true, true);
    }

    @Test
    public final void testBadCommand() throws InterruptedException {
        Ros2Topics.main(new String[]{"foo"});
        assertEquals(true, true);
    }

    @Test
    public final void testEcho() throws InterruptedException {
        Ros2Topics.main(new String[]{"echo"});
        Ros2Topics.main(new String[]{"echo", "/rosout"});
        Ros2Topics.main(new String[]{"echo", "/rosout", "std_msgs/String", "2"});
        assertEquals(true, true);
    }

    @Test
    public final void testFind() throws InterruptedException {
        Ros2Topics.main(new String[]{"find"});
        Ros2Topics.main(new String[]{"find", "std_msgs/String"});
        assertEquals(true, true);
    }

    @Test
    public final void testHz() throws InterruptedException {
        Ros2Topics.main(new String[]{"hz"});
        assertEquals(true, true);
    }

    @Test
    public final void testList() throws InterruptedException {
        Ros2Topics.main(new String[]{"list"});
        assertEquals(true, true);
    }

    @Test
    public final void testPub() throws InterruptedException {
        Ros2Topics.main(new String[]{"pub"});
        Ros2Topics.main(new String[]{"pub", "/rosout"});
        Ros2Topics.main(new String[]{"pub", "/rosout", "std_msgs/String"});
        Ros2Topics.main(new String[]{"pub", "/rosout", "std_msgs/String", "{ \"data\":\"iii\" }", "-1"});
        assertEquals(true, true);
    }

    @Test
    public final void testType() throws InterruptedException {
        Ros2Topics.main(new String[]{"type"});
        Ros2Topics.main(new String[]{"type", "/rosout"});
        assertEquals(true, true);
    }

    @Test
    public final void testInfo() throws InterruptedException {
        Ros2Topics.main(new String[]{"info"});
        assertEquals(true, true);
    }

    @Test
    public final void testBw() throws InterruptedException {
        Ros2Topics.main(new String[]{"bw"});
        assertEquals(true, true);
    }

    @Test
    public final void testDelay() throws InterruptedException {
        Ros2Topics.main(new String[]{"delay"});
        assertEquals(true, true);
    }

}
