package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.Test;

import org.ros2.rcljava.tool.Ros2Nodes;

public class TestRos2Nodes {

    @Test
    public final void testNodes() throws InterruptedException {
        Ros2Nodes.main(new String[]{});
        assertEquals(true, true);
    }

}
