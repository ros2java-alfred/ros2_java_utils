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

import java.util.concurrent.ConcurrentSkipListSet;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;

/**
 * Node tool CLI
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class Ros2Nodes {
    private static String NAME = Ros2Nodes.class.getSimpleName().toLowerCase();


    public static void main(String[] args) throws InterruptedException {

        // Initialize RCL
        RCLJava.rclJavaInit();
        Node node = RCLJava.createNode(NAME);
        ConcurrentSkipListSet<String> nodeNames = new ConcurrentSkipListSet<String>(node.getNodeNames());
        RCLJava.spinOnce(node);

        if (nodeNames.size() > 0) {
            for (String entity : nodeNames) {
                System.out.println(String.format("%s", entity));
            }
        }else {
            System.out.println("Empty topics !");
        }

        node.dispose();
        RCLJava.shutdown();
    }
}
