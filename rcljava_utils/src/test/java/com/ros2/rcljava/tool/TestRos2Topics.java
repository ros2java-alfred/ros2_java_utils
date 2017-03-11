package com.ros2.rcljava.tool;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ros2.rcljava.tool.Ros2Topics;

public class TestRos2Topics {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Ignore
    public final void testFullUsage() throws InterruptedException {
        Ros2Topics.main(new String[]{});
        assertEquals(true, true);
    }

    @Test
    public final void testEcho() throws InterruptedException {
        Ros2Topics.main(new String[]{"echo"});
        assertEquals(true, true);
    }

    @Test
    public final void testFind() throws InterruptedException {
        Ros2Topics.main(new String[]{"find"});
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
        assertEquals(true, true);
    }

    @Test
    public final void testType() throws InterruptedException {
        Ros2Topics.main(new String[]{"type"});
        assertEquals(true, true);
    }

}
