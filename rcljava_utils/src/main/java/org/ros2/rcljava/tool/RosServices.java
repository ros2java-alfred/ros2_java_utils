/* Copyright 2016 Open Source Robotics Foundation, Inc.
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

import java.util.HashMap;
import java.util.Map.Entry;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;

/**
 * Service tool CLI
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class RosServices {
    private static String NAME = RosServices.class.getSimpleName();
//  private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    private final static String CMD_ECHO    = "echo";
    private final static String CMD_TYPE    = "type";
    private final static String CMD_LIST    = "list";
    private final static String CMD_INFO    = "info";
    private final static String CMD_PUB     = "pub";
    private final static String CMD_BW      = "bw";
    private final static String CMD_FIND    = "find";

    private static void fullUsage() {
        System.out.println("rosservice_java is a command-line tool for printing information about ROS Services.\n" +
        "Commands:\n" +
//        "\trosservice_java bw\tdisplay bandwidth used by topic\n" +
        "\trosservice_java echo\tprint messages to screen\n" +
        "\trosservice_java find\tfind service by type\n" +
//        "\trosservice_java info\tprint information about active topic\n" +
        "\trosservice_java list\tlist active services\n" +
        "\trosservice_java pub\trequest data to service\n" +
        "\trosservice_java type\tprint service type\n" +
        "Type rosservice_java <command> -h for more detailed usage, e.g. 'rosservice_java echo -h'\n");
        System.exit(1);
    }

    private static void rostopicCmdFind(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("service type must be specified");
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

    private static void rostopicCmdBw(String[] args) {
        // TODO Auto-generated method stub

    }

    private static void rostopicCmdPub(String[] args) {
        // TODO Auto-generated method stub

    }

    private static void rostopicCmdInfo(String[] args) {
        // TODO Auto-generated method stub

    }

    private static void rostopicCmdList(String[] args) {
        Node node = RCLJava.createNode(NAME);
        HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

        if (topicsTypes.size() > 0) {
            for (Entry<String, String> entity : topicsTypes.entrySet()) {
                if (entity.getKey().endsWith("Reply")) {
                    System.out.println(String.format("%s", entity.getKey().replace("Reply", "")));
                }
            }
        }else {
            System.out.println("Empty Service !");
        }

        node.dispose();

    }

    private static void rostopicCmdType(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length < 2) {
            System.out.println("Need to add service name.");
        } else
        if (args.length > 2) {
            System.out.println("You may only specify one input service.");
        } else {

            String topicPath = args[1];
            HashMap<String, String > topicsTypes = node.getTopicNamesAndTypes();

            if (topicsTypes.size() > 0) {
                topicPath += "Reply";
                if (topicsTypes.containsKey(topicPath)) {
                        String type = topicsTypes.get(topicPath);
                        System.out.println(String.format("%s", type.replace("::srv::dds_", "").replace("_Response_", "").replace("::", "/")));
                } else {
                    System.out.println("No Service " + topicPath + " available !");
                }
            }else {
                System.out.println("No Service available !");
            }
        }

        node.dispose();
    }

    private static void rostopicCmdEcho(String[] args) {
        // TODO Auto-generated method stub

    }



    public static void main(String[] args) throws InterruptedException {
//      logger.setLevel(Level.ALL);
//      ConsoleHandler handler = new ConsoleHandler();
//      handler.setFormatter(new SimpleFormatter());
//      logger.addHandler(handler);
//      handler.setLevel(Level.ALL);

      if (args.length == 0) {
          fullUsage();
      }

      // Initialize RCL
      RCLJava.rclJavaInit();

      try {
          switch (args[0]) {
          case CMD_ECHO:
              RosServices.rostopicCmdEcho(args);
              break;
          case CMD_TYPE:
              RosServices.rostopicCmdType(args);
              break;
          case CMD_LIST:
              RosServices.rostopicCmdList(args);
              break;
          case CMD_INFO:
              RosServices.rostopicCmdInfo(args);
              break;
          case CMD_PUB:
              RosServices.rostopicCmdPub(args);
              break;
          case CMD_BW:
              RosServices.rostopicCmdBw(args);
              break;
          case CMD_FIND:
              RosServices.rostopicCmdFind(args);
              break;
          default:
              RosServices.fullUsage();
              break;
          }
      } catch (Exception e) {
          System.out.println("ERROR : " + e.getMessage());
          e.printStackTrace();
      }

      RCLJava.shutdown();
  }
}
