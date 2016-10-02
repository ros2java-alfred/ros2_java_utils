# rcljava_utils

rostopic_java
```
rostopic_java is a command-line tool for printing information about ROS2 Topics.
Commands:
    rostopic echo   print messages to screen
    rostopic find   find topics by type
    rostopic hz     display publishing rate of topic
    rostopic list   list active topics
    rostopic pub    publish data to topic
    rostopic type   print topic type
Type rostopic_java <command> -h for more detailed usage, e.g. 'rostopic echo -h'
```

### List active topics
`rostopic_java list`

### Print topic type
`rostopic_java type /topic`

### Find topics by type
`rostopic_java find msgs/Message`

### Print messages to screen
`rostopic_java echo /topic msgs/Message`

### Publish data to topic
`rostopic_java pub /topic msgs/Message '{message}'`

