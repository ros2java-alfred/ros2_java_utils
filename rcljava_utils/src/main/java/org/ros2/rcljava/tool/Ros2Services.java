/* Copyright 2016-2017 Mickael Gaillard <mick.gaillard@gmail.com>
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

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Future;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.internal.message.Message;
import org.ros2.rcljava.internal.service.MessageService;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.service.Client;

import com.google.gson.Gson;

/**
 * Service tool CLI
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public abstract class Ros2Services {
    private static String NAME = "_" + Ros2Services.class.getSimpleName().toLowerCase();

    private final static String CMD_TYPE    = "type";
    private final static String CMD_LIST    = "list";
    private final static String CMD_INFO    = "info";
    private final static String CMD_REQ     = "req";
    private final static String CMD_FIND    = "find";

    private static void fullUsage() {
        System.out.println("ros2service_java is a command-line tool for printing information about ROS Services.\n" +
        "Commands:\n" +
        "\tros2service_java find\tfind service by type\n" +
//        "\tros2service_java info\tprint information about active topic\n" +
        "\tros2service_java list\tlist active services\n" +
        "\tros2service_java req\trequest data to service\n" +
        "\tros2service_java type\tprint service type\n" +
        "Type rosservice_java <command> -h for more detailed usage, e.g. 'ros2service_java find -h'\n");
//        System.exit(1); // Bad choice !!
    }

    private static void rosserviceCmdFind(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("service type must be specified");
        } else {
            String nametype = args[1];
            RCLJava.spinOnce(node);
            ConcurrentSkipListMap<String, List<String>> topicsTypes = new ConcurrentSkipListMap<String, List<String>>(node.getServiceNamesAndTypes());

//            if (topicsTypes.containsValue(nametype)) {
                for (Entry<String, List<String>> entity : topicsTypes.entrySet()) {
                    for (String type : entity.getValue()) {
                        if (Ros2Services.messageConverteur(type).equals(nametype)) {
                            System.out.println(String.format("%s", entity.getKey().replace("Reply", "")));
                        }
                    }
                }
//            }
        }

        node.dispose();

    }

    private static String messageConverteur(String internalMessage) {
        String displayMessage = internalMessage
                .replace("::srv::dds_", "")
                .replace("_Response_", "")
//                .replace("_Request_", "")
                .replace("::", "/");

        return displayMessage;
    }

    private static void rosserviceCmdReq(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length == 1) {
            System.out.println("/topic must be specified");
        }
        else if (args.length == 2) {
            System.out.println("topic type must be specified");
        }
        else if (args.length == 3) {
                System.out.println("topic data request must be specified");
        } else {
            String topic = args[1];
            String serviceTypeName = args[2];
            String serviceJson = args[3];

            final Class<MessageService> messageType = Ros2Services.loadServiceMessage(serviceTypeName);
            final Gson gson = new Gson();
            System.out.println(messageType);

            try {
                Client<MessageService> client = node.<MessageService>createClient(messageType, topic);

                // Set request.
                MessageService msg = messageType.newInstance();
                Message request = gson.fromJson(serviceJson, msg.getRequestType());

                // Call service...
                Future<Message> future = client.sendRequest(request);
                RCLJava.spinOnce(node);
                if (future != null) {
                    Message responce =  future.get();
                    System.out.println(String.format("Result : %s", gson.toJson(responce)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        node.dispose();
    }

    @SuppressWarnings("unchecked")
    public static Class<MessageService> loadServiceMessage(String serviceTypeName) {
        Class<MessageService> serviceType = null;

        try {
            serviceType = (Class<MessageService>) Class.forName(serviceTypeName.replaceFirst("/", ".srv."));
        } catch (ClassNotFoundException e1) {
            System.out.println("Service not found !");
            e1.printStackTrace();
        }

        return serviceType;
    }

    @SuppressWarnings("unchecked")
    public static Class<Message> loadInternalMessage(String serviceTypeName) {
        Class<Message> serviceType = null;

        try {
            serviceType = (Class<Message>) Class.forName(serviceTypeName.replaceFirst("/", ".srv."));
        } catch (ClassNotFoundException e1) {
            System.out.println("Message not found !");
            e1.printStackTrace();
        }

        return serviceType;
    }

    private static void rosserviceCmdInfo(String[] args) {
        // TODO Auto-generated method stub

    }

    private static void rosserviceCmdList(String[] args) {
        Node node = RCLJava.createNode(NAME);
        RCLJava.spinOnce(node);
        ConcurrentSkipListMap<String, List<String>> topicsTypes = new ConcurrentSkipListMap<String, List<String>>(node.getServiceNamesAndTypes());

        if (topicsTypes.size() > 0) {
            for (Entry<String, List<String>> entity : topicsTypes.entrySet()) {
              System.out.println(String.format("%s", entity.getKey()));
            }
        }else {
            System.out.println("Empty Service !");
        }

        node.dispose();
    }

    private static void rosserviceCmdType(String[] args) {
        Node node = RCLJava.createNode(NAME);

        if (args.length < 2) {
            System.out.println("Need to add service name.");
        } else
        if (args.length > 2) {
            System.out.println("You may only specify one input service.");
        } else {

            String topicPath = args[1];
            RCLJava.spinOnce(node);
            ConcurrentSkipListMap<String, List<String>> topicsTypes = new ConcurrentSkipListMap<String, List<String>>(node.getServiceNamesAndTypes());

            if (topicsTypes.size() > 0) {
                if (topicsTypes.containsKey(topicPath)) {
                    List<String> types = topicsTypes.get(topicPath);
                    for (String type : types) {
                    System.out.println(String.format("%s", Ros2Services.messageConverteur(type)));
                    }
                } else {
                    System.out.println("No Service " + topicPath + " available !");
                }
            }else {
                System.out.println("No Service available !");
            }
        }

        node.dispose();
    }



    public static void main(String[] args) throws InterruptedException {

      if (args.length == 0) {
          fullUsage();
          return;
      }

      // Initialize RCL
      RCLJava.rclJavaInit(args);

      try {
          switch (args[0]) {
          case CMD_TYPE:
              Ros2Services.rosserviceCmdType(args);
              break;
          case CMD_LIST:
              Ros2Services.rosserviceCmdList(args);
              break;
          case CMD_INFO:
              Ros2Services.rosserviceCmdInfo(args);
              break;
          case CMD_REQ:
              Ros2Services.rosserviceCmdReq(args);
              break;
          case CMD_FIND:
              Ros2Services.rosserviceCmdFind(args);
              break;
          default:
              Ros2Services.fullUsage();
              break;
          }
      } catch (Exception e) {
          System.out.println("ERROR : " + e.getMessage());
          e.printStackTrace();
      }

      RCLJava.shutdown();
  }
}
