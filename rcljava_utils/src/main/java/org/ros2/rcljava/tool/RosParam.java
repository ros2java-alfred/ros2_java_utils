package org.ros2.rcljava.tool;

import java.util.Arrays;
import java.util.List;

import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.parameter.ParameterVariant;
import org.ros2.rcljava.node.parameter.SyncParametersClient;

public class RosParam {

    private static String NAME = RosParam.class.getSimpleName().toLowerCase();

    private static final String HELP =
            "USAGE:\n"+
            "  rosparam_java get <node/variable>\n"+
            "  rosparam_java set <node/variable> <value>\n" +
            "  rosparam_java list <node>";

    enum Usage {
        PARAM_NONE,
        PARAM_GET,
        PARAM_SET,
        PARAM_LIST,
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        Usage op = Usage.PARAM_NONE;
        Node node = RCLJava.createNode(NAME);

        String remotenode = args[0];
        String param = args[1];

        try {
            SyncParametersClient parameters_client = new SyncParametersClient(node, remotenode, QoSProfile.PARAMETER);

            // Get a few of the parameters just set.
            List<ParameterVariant<?>> parameters = parameters_client.getParameters(Arrays.asList(param));
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
