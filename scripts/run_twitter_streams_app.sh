#!/bin/bash
docker cp $(pwd)/TwitterChangesMonitor/target/twitterchange-1.0-SNAPSHOT-standalone.jar connect:/tmp/
docker exec -it connect java -cp /tmp/twitterchange-1.0-SNAPSHOT-standalone.jar io.confluent.examples.kafka.streams.twitter.StatusMonitor
