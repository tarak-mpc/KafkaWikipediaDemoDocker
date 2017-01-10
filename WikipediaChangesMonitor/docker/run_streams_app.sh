#!/bin/bash
echo $(pwd)
docker cp $(pwd)/../target/changesmonitor-1.0-SNAPSHOT-standalone.jar confluentplatformwikipediademo_connect_1:/tmp/
docker exec -it confluentplatformwikipediademo_connect_1 java -cp /tmp/changesmonitor-1.0-SNAPSHOT-standalone.jar org.cmatta.kafka.streams.wikipedia.MessageMonitor
