FROM confluentinc/cp-base:latest

ADD target/changesmonitor-1.0-SNAPSHOT-standalone.jar /root/
CMD ['java', '-cp', '/root/changesmonitor-1.0-SNAPSHOT-standalone.jar', 'org.cmatta.kafka.streams.wikipedia.MessageMonitor']
