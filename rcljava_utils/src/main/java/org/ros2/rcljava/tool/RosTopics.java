package org.ros2.rcljava.tool;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;


public class RosTopics {

    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    private final static String CMD_ECHO    = "echo";
    private final static String CMD_HZ      = "hz";
    private final static String CMD_TYPE    = "type";
    private final static String CMD_LIST    = "list";
    private final static String CMD_INFO    = "info";
    private final static String CMD_PUB     = "pub";
    private final static String CMD_BW      = "bw";
    private final static String CMD_FIND    = "find";
    private final static String CMD_DELAY   = "delay";

    private static void fullUsage() {
        System.out.println("rostopic is a command-line tool for printing information about ROS Topics.\n" +
        "Commands:\n" +
        "\trostopic bw\tdisplay bandwidth used by topic\n" +
        "\trostopic delay\tdisplay delay of topic from timestamp in header\n" +
        "\trostopic echo\tprint messages to screen\n" +
        "\trostopic find\tfind topics by type\n" +
        "\trostopic hz\tdisplay publishing rate of topic    \n" +
        "\trostopic info\tprint information about active topic\n" +
        "\trostopic list\tlist active topics\n" +
        "\trostopic pub\tpublish data to topic\n" +
        "\trostopic type\tprint topic type\n" +
        "Type rostopic <command> -h for more detailed usage, e.g. 'rostopic echo -h'\n");
        System.exit(1);
    }

    private static void rostopicCmdHz() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdType() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdList() {
        // Let's create a Node
        Node node = RCLJava.createNode("_debug");
        HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

        if (topicsTypes.size() > 0) {
            for (Entry<String, String> entity : topicsTypes.entrySet()) {
                    System.out.println(String.format("%s [%s]", entity.getKey(), entity.getValue()));
            }
        }else {
            System.out.println("Empty topics !");
        }

        node.dispose();
    }

    private static void rostopicCmdInfo() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdPub() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdBw() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdFind() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdDelay() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdEcho() {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }



    public static void main(String[] args) throws InterruptedException {
//        logger.setLevel(Level.ALL);
//        ConsoleHandler handler = new ConsoleHandler();
//        handler.setFormatter(new SimpleFormatter());
//        logger.addHandler(handler);
//        handler.setLevel(Level.ALL);

        if (args.length == 0) {
            fullUsage();
        }

        // Initialize RCL
        RCLJava.rclJavaInit();

        try {
            switch (args[0]) {
            case CMD_ECHO:
                rostopicCmdEcho();
                break;
            case CMD_HZ:
                rostopicCmdHz();
                break;
            case CMD_TYPE:
                rostopicCmdType();
                break;
            case CMD_LIST:
                rostopicCmdList();
                break;
            case CMD_INFO:
                rostopicCmdInfo();
                break;
            case CMD_PUB:
                rostopicCmdPub();
                break;
            case CMD_BW:
                rostopicCmdBw();
                break;
            case CMD_FIND:
                rostopicCmdFind();
                break;
            case CMD_DELAY:
                rostopicCmdDelay();
                break;
            default:
                fullUsage();
                break;
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }

        RCLJava.shutdown();
    }
}
