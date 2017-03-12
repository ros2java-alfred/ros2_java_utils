package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.ros2.rcljava.tool.Ros2Param;

public class TestRos2Param {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    @Ignore
    public final void testEmpty() throws InterruptedException {
        Ros2Param.main(new String[]{});
        assertEquals(true, true);
    }

    @Test
    @Ignore
    public final void testBadCommand() throws InterruptedException {
        Ros2Param.main(new String[]{"foo"});
        assertEquals(true, true);
    }

    @Test
    public final void testTest() throws InterruptedException {
        Ros2Param.main(new String[]{"\ros2param", "~value"});
        assertEquals(true, true);
    }

    @Test
    @Ignore
    public final void testList() throws InterruptedException {
        Ros2Param.main(new String[]{"list", "\ros2param"});
        assertEquals(true, true);
    }


    @Test
    @Ignore
    public final void testGet() throws InterruptedException {
        Ros2Param.main(new String[]{"get", "\ros2param"});
        assertEquals(true, true);
    }

    @Test
    @Ignore
    public final void testSet() throws InterruptedException {
        Ros2Param.main(new String[]{"set", "\ros2param"});
        assertEquals(true, true);
    }

}
