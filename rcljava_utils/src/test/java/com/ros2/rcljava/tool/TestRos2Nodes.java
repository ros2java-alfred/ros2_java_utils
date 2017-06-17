package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.ros2.rcljava.tool.Ros2Nodes;

public class TestRos2Nodes {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(3); // 10 seconds max per method tested

    @Test
    public final void testNodes() throws InterruptedException {
        Ros2Nodes.main(new String[]{});
        assertEquals(true, true);
    }

    @Test
    public final void testNodesEmpty() throws InterruptedException {
        Ros2Nodes.main(new String[]{});
        assertEquals(true, true);
    }

}
