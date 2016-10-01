package org.ros2.rcljava.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;

public class RosNodes {

    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    public static void main(String[] args) throws InterruptedException {
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        handler.setLevel(Level.ALL);

        // Initialize RCL
        RCLJava.rclJavaInit();

        System.out.println("List of Nodes :");
        List<String> nodeNames = RCLJava.getNodeNames();

        if (nodeNames.size() > 0) {
            for (String entity : nodeNames) {
                    System.out.println(String.format("\t|>%s", entity));
            }
        }else {
            System.out.println("Empty topics !");
        }

        RCLJava.shutdown();
    }
}
