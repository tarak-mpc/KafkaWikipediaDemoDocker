#!/bin/bash
docker cp $(pwd)/WikipediaChangesMonitor/target/changesmonitor-1.0-SNAPSHOT-standalone.jar connect:/tmp/
docker exec -it connect java -cp /tmp/changesmonitor-1.0-SNAPSHOT-standalone.jar org.cmatta.kafka.streams.wikipedia.MessageMonitor
