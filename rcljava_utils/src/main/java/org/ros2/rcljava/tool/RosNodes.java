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

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.ros2.rcljava.RCLJava;

/**
 * Node tool CLI
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
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
