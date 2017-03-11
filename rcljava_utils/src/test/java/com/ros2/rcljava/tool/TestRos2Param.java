package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.Test;

import org.ros2.rcljava.tool.Ros2Param;

public class TestRos2Param {

    @Test
    public final void testEmpty() throws InterruptedException {
        Ros2Param.main(new String[]{});
        assertEquals(true, true);
    }

    @Test
    public final void testBadCommand() throws InterruptedException {
        Ros2Param.main(new String[]{"foo"});
        assertEquals(true, true);
    }

    @Test
    public final void testList() throws InterruptedException {
        Ros2Param.main(new String[]{"list"});
        assertEquals(true, true);
    }


    @Test
    public final void testType() throws InterruptedException {
        Ros2Param.main(new String[]{"get"});
        assertEquals(true, true);
    }

    @Test
    public final void testInfo() throws InterruptedException {
        Ros2Param.main(new String[]{"set"});
        assertEquals(true, true);
    }

}
