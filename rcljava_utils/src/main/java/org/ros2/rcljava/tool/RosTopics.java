package org.ros2.rcljava.tool;

import java.util.HashMap;
import java.util.Map.Entry;

import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.internal.message.Message;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.topic.Consumer;
import org.ros2.rcljava.node.topic.Publisher;
import org.ros2.rcljava.node.topic.Subscription;

import com.google.gson.Gson;


public class RosTopics {

    private static String NAME = RosTopics.class.getSimpleName();
//    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

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
//        "\trostopic bw\tdisplay bandwidth used by topic\n" +
//        "\trostopic delay\tdisplay delay of topic from timestamp in header\n" +
        "\trostopic echo\tprint messages to screen\n" +
        "\trostopic find\tfind topics by type\n" +
//        "\trostopic hz\tdisplay publishing rate of topic    \n" +
//        "\trostopic info\tprint information about active topic\n" +
        "\trostopic list\tlist active topics\n" +
        "\trostopic pub\tpublish data to topic\n" +
        "\trostopic type\tprint topic type\n" +
        "Type rostopic <command> -h for more detailed usage, e.g. 'rostopic echo -h'\n");
        System.exit(1);
    }

    private static void rostopicCmdHz(String[] args) {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdType(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length < 2) {
            System.out.println("Need to add topic name.");
        } else
        if (args.length > 2) {
            System.out.println("You may only specify one input topic.");
        } else {

            String topicPath = args[1];
            HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

            if (topicsTypes.size() > 0) {
                if (topicsTypes.containsKey(topicPath)) {
                    System.out.println(String.format("%s", topicsTypes.get(topicPath)));
                } else {
                    System.out.println("No Topic " + topicPath + " available !");
                }
            }else {
                System.out.println("No Topics available !");
            }
        }

        node.dispose();
    }

    private static void rostopicCmdList(String[] args) {
        Node node = RCLJava.createNode(NAME);
        HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

        if (topicsTypes.size() > 0) {
            for (Entry<String, String> entity : topicsTypes.entrySet()) {
                System.out.println(String.format("%s", entity.getKey()));
            }
        }else {
            System.out.println("Empty topics !");
        }

        node.dispose();
    }

    private static void rostopicCmdInfo(String[] args) {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdPub(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("/topic must be specified");
        } else
        if (args.length == 2) {
            System.out.println("topic type must be specified");
        } else
        if (args.length == 3) {
            System.out.println("message must be specified");
        } else {

            final Gson gson = new Gson();
            String topic = args[1];
            String messageTypeName = args[2];
            String messageJson = args[3];

            Class<Message> messageType = RosTopics.loadMessage(messageTypeName);
            Message message = gson.fromJson(messageJson, messageType);

            Publisher<Message> pub =
                    node.<Message>createPublisher(
                        messageType,
                        topic,
                        QoSProfile.PROFILE_DEFAULT);

            while(RCLJava.ok()) {
                System.out.println("Publishing: " + gson.toJson(message));
                pub.publish(message);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            pub.dispose();
        }

        node.dispose();
    }

    private static void rostopicCmdBw(String[] args) {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdFind(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("topic type must be specified");
        } else {
            String nametype = args[1];
            HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

            if (topicsTypes.containsValue(nametype)) {
                for (Entry<String, String> entity : topicsTypes.entrySet()) {
                    if (entity.getValue().equals(nametype)) {
                        System.out.println(String.format("%s", entity.getKey()));
                    }
                }
            }
        }

        node.dispose();
    }

    private static void rostopicCmdDelay(String[] args) {
        //TODO not implemented.
        System.out.println("Not implemented...");
    }

    private static void rostopicCmdEcho(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("/topic must be specified");
        }
        else if (args.length == 2) {
            System.out.println("topic type must be specified");
        } else {
            String topic = args[1];
            String messageTypeName = args[2];

            final Class<Message> messageType = RosTopics.loadMessage(messageTypeName);
            final Gson gson = new Gson();

            Subscription<Message> sub = node.<Message>createSubscription(
                    messageType,
                    topic,
                    new Consumer<Message>() {
                        @Override
                        public void accept(Message msg) {
                            System.out.println(gson.toJson(msg));
                        }
                    },
                    QoSProfile.PROFILE_DEFAULT);

            RCLJava.spin(node);

            sub.dispose();
            }

        node.dispose();
    }

    @SuppressWarnings("unchecked")
    private static Class<Message> loadMessage(String messageTypeName) {
        Class<Message> messageType = null;

        try {
            messageType = (Class<Message>) Class.forName(messageTypeName.replaceFirst("/", ".msg."));
        } catch (ClassNotFoundException e1) {
            System.out.println("Message not found !");
            e1.printStackTrace();
        }

        return messageType;
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
                RosTopics.rostopicCmdEcho(args);
                break;
            case CMD_HZ:
                RosTopics.rostopicCmdHz(args);
                break;
            case CMD_TYPE:
                RosTopics.rostopicCmdType(args);
                break;
            case CMD_LIST:
                RosTopics.rostopicCmdList(args);
                break;
            case CMD_INFO:
                RosTopics.rostopicCmdInfo(args);
                break;
            case CMD_PUB:
                RosTopics.rostopicCmdPub(args);
                break;
            case CMD_BW:
                RosTopics.rostopicCmdBw(args);
                break;
            case CMD_FIND:
                RosTopics.rostopicCmdFind(args);
                break;
            case CMD_DELAY:
                RosTopics.rostopicCmdDelay(args);
                break;
            default:
                RosTopics.fullUsage();
                break;
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            e.printStackTrace();
        }

        RCLJava.shutdown();
    }
}
