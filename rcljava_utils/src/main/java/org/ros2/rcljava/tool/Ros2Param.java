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

import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.parameter.ParameterVariant;
import org.ros2.rcljava.node.parameter.SyncParametersClient;

public abstract class Ros2Param {

    private static String NAME = "_" + Ros2Param.class.getSimpleName().toLowerCase();

//    private static final String HELP =
//            "USAGE:\n"+
//            "  ros2param_java get <node/variable>\n"+
//            "  ros2param_java set <node/variable> <value>\n" +
//            "  ros2param_java list <node>";

    enum Usage {
        PARAM_NONE,
        PARAM_GET,
        PARAM_SET,
        PARAM_LIST,
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

//        Usage op = Usage.PARAM_NONE;
        Node node = RCLJava.createNode(NAME);

        String remotenode = args[0];
        String param = args[1];

        try {
            SyncParametersClient parameters_client = new SyncParametersClient(node, remotenode, QoSProfile.PARAMETER);

            // Get a few of the parameters just set.
            ConcurrentSkipListSet<ParameterVariant<?>> parameters =
                    new ConcurrentSkipListSet<ParameterVariant<?>>(parameters_client.getParameters(Arrays.asList(param)));

            if (parameters.size() > 0) {
                for (ParameterVariant<?> parameter : parameters) {
                    System.out.println(parameter.valueToString());
                }
            } else {
                System.out.println("Node or Parameter not found");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        node.dispose();
        RCLJava.shutdown();
    }
}
