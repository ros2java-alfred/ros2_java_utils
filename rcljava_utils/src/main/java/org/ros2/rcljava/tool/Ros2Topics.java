/* Copyright 2016 Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ros2.rcljava.tool;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.internal.message.Message;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.topic.Consumer;
import org.ros2.rcljava.node.topic.Publisher;
import org.ros2.rcljava.node.topic.Subscription;

import com.google.gson.Gson;

/**
 * Topic tool CLI
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class Ros2Topics {

    private static String NAME = Ros2Topics.class.getSimpleName().toLowerCase();

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
        System.out.println("rostopic_java is a command-line tool for printing information about ROS Topics.\n" +
        "Commands:\n" +
//        "\trostopic_java bw\tdisplay bandwidth used by topic\n" +
//        "\trostopic_java delay\tdisplay delay of topic from timestamp in header\n" +
        "\trostopic_java echo\tprint messages to screen\n" +
        "\trostopic_java find\tfind topics by type\n" +
        "\trostopic_java hz\tdisplay publishing rate of topic    \n" +
//        "\trostopic_java info\tprint information about active topic\n" +
        "\trostopic_java list\tlist active topics\n" +
        "\trostopic_java pub\tpublish data to topic\n" +
        "\trostopic_java type\tprint topic type\n" +
        "Type rostopic_java <command> -h for more detailed usage, e.g. 'rostopic echo -h'\n");
        System.exit(1);
    }

    private static void rostopicCmdHz(String[] args) {
        Ros2Topics.rostopicCmdEchoOrRate(args, true);
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
            ConcurrentSkipListMap<String, String > topicsTypes = new ConcurrentSkipListMap<String, String>(node.getTopicNamesAndTypes());
            RCLJava.spinOnce(node);

            topicsTypes.putAll(node.getTopicNamesAndTypes());

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
        ConcurrentSkipListMap<String, String > topicsTypes = new ConcurrentSkipListMap<String, String>(node.getTopicNamesAndTypes());
        RCLJava.spinOnce(node);

        topicsTypes.putAll(node.getTopicNamesAndTypes());

        if (topicsTypes.size() > 0) {
            for (Entry<String, String> entity : topicsTypes.entrySet()) {
                if (!entity.getKey().endsWith("Reply") && !entity.getKey().endsWith("Request")) {
                    System.out.println(String.format("%s", entity.getKey()));
                }
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
            int i = 0;
            final Gson gson = new Gson();
            String topic = args[1];
            String messageTypeName = args[2];
            String messageJson = args[3];
            int rate = 1;

            // Parse Rate
            if (args.length == 5) {
                String rateArg = args[4];
                rate = Integer.parseInt(rateArg);

                if (rate == 0) {
                    rate = 1;
                }
            }

            Class<Message> messageType = Ros2Topics.loadMessage(messageTypeName);
            Message message = gson.fromJson(messageJson, messageType);

            Publisher<Message> pub =
                    node.<Message>createPublisher(
                        messageType,
                        topic,
                        QoSProfile.DEFAULT);

            while(RCLJava.ok()) {
                // Hack for counter in message
                if (messageJson.contains("iii")) {
                    String messageJsonCp = new String(messageJson);
                    messageJsonCp = messageJsonCp.replaceAll("iii", String.valueOf(++i));
                    message = gson.fromJson(messageJsonCp, messageType);
                }

                // Publish
                System.out.println("Publishing: " + gson.toJson(message));
                pub.publish(message);

                // Only once publish
                if (rate == -1) {
                    break;
                }

                // Rate Sleep
                try {
                    Thread.sleep(1000/rate);
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
            ConcurrentSkipListMap<String, String > topicsTypes = new ConcurrentSkipListMap<String, String>(node.getTopicNamesAndTypes());
            RCLJava.spinOnce(node);

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
        Ros2Topics.rostopicCmdEchoOrRate(args, false);
    }

    private static void rostopicCmdEchoOrRate(String[] args,final boolean rate) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("/topic must be specified");
        }
        else if (args.length == 2) {
            System.out.println("topic type must be specified");
        } else {
            String topic = args[1];
            String messageTypeName = args[2];

            Class<Message> messageType = Ros2Topics.loadMessage(messageTypeName);
            if (messageType == null) {
                messageType = Ros2Services.loadInternalMessage(messageTypeName);
            }
            final Gson gson = new Gson();

            Subscription<Message> sub = node.<Message>createSubscription(
                    messageType,
                    topic,
                    new Consumer<Message>() {
                        long startTime = System.nanoTime();
                        long lastTime = System.nanoTime();

                        @Override
                        public void accept(Message msg) {
                            if (rate) {

                                long update = (System.nanoTime() - startTime) / 1000000000;
                                if (update > 1 ) {

                                    double estimatedTime = 1000000000.0 / (float)(System.nanoTime() - lastTime);
                                    System.out.println(String.format("Freq : %f hz", estimatedTime));

                                    startTime = System.nanoTime();
                                }

                                lastTime = System.nanoTime();
                            } else {
                                System.out.println(gson.toJson(msg));
                            }
                        }
                    },
                    QoSProfile.DEFAULT);

            RCLJava.spin(node);

            sub.dispose();
            }

        node.dispose();
    }

    @SuppressWarnings("unchecked")
    public static Class<Message> loadMessage(String messageTypeName) {
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

        if (args.length == 0) {
            fullUsage();
        }

        // Initialize RCL
        RCLJava.rclJavaInit();

        try {
            switch (args[0]) {
            case CMD_ECHO:
                Ros2Topics.rostopicCmdEcho(args);
                break;
            case CMD_HZ:
                Ros2Topics.rostopicCmdHz(args);
                break;
            case CMD_TYPE:
                Ros2Topics.rostopicCmdType(args);
                break;
            case CMD_LIST:
                Ros2Topics.rostopicCmdList(args);
                break;
            case CMD_INFO:
                Ros2Topics.rostopicCmdInfo(args);
                break;
            case CMD_PUB:
                Ros2Topics.rostopicCmdPub(args);
                break;
            case CMD_BW:
                Ros2Topics.rostopicCmdBw(args);
                break;
            case CMD_FIND:
                Ros2Topics.rostopicCmdFind(args);
                break;
            case CMD_DELAY:
                Ros2Topics.rostopicCmdDelay(args);
                break;
            default:
                Ros2Topics.fullUsage();
                break;
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            e.printStackTrace();
        }

        RCLJava.shutdown();
    }
}
