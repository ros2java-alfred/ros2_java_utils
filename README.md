# rcljava_utils [![Build Status](https://travis-ci.org/ros2java-alfred/ros2_java_utils.svg?branch=master)](https://travis-ci.org/ros2java-alfred/ros2_java_utils) [![Coverage Status](https://coveralls.io/repos/github/ros2java-alfred/ros2_java_utils/badge.svg?branch=master)](https://coveralls.io/github/ros2java-alfred/ros2_java_utils?branch=master) [![Code Climate](https://codeclimate.com/github/ros2java-alfred/ros2_java_utils/badges/gpa.svg)](https://codeclimate.com/github/ros2java-alfred/ros2_java_utils)

## ros2nodes_java

## ros2topic_java
```
ros2topic_java is a command-line tool for printing information about ROS2 Topics.
Commands:
    ros2topic_java echo   print messages to screen
    ros2topic_java find   find topics by type
    ros2topic_java hz     display publishing rate of topic
    ros2topic_java list   list active topics
    ros2topic_java pub    publish data to topic
    ros2topic_java type   print topic type
Type ros2topic_java <command> -h for more detailed usage, e.g. 'ros2topic echo -h'
```

### List active topics
`ros2topic_java list`

### Print topic type
`ros2topic_java type /topic`

### Find topics by type
`ros2topic_java find msgs/Message`

### Print messages to screen
`ros2topic_java echo /topic msgs/Message`

### Publish data to topic
`ros2topic_java pub /topic msgs/Message '{message}'`

## ros2services_java

## ros2param_java
