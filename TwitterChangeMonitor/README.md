###WikipediaChangesMonitor

Use in conjunction with [kafka-connect-irc](https://github.com/cjmatta/kafka-connect-irc) to monitor Wikipedia channels and transform the messages to proper Avro.

**Avro message format**

```
{
  "namespace": "org.cmatta.kafka.streams.wikipedia.avro",
    "type": "record",
      "name": "WikipediaChange",
      "fields": [
      {
            "name": "createdat",
                  "type": "long"
                      
      },
      {
            "name": "wikipage",
                  "type": "string"
                      
      },
      {
            "name": "isnew",
                  "type": "boolean"
                      
      },
      {
            "name": "isminor",
                  "type": "boolean"
                      
      },
      {
      "name": "isunpatrolled",
            "type": "boolean"
                
      },
      {
            "name": "isbot",
                  "type": "boolean"
                      
      },
      {
      "name": "diffurl",
            "type": "string"
                
      },
      {
            "name": "username",
                  "type": "string"
                      
      },
      {
      "name": "bytechange",
            "type": "int"
                
      },
      {
            "name": "commitmessage",
                  "type": "string"
                      
      }
        
      ]

}
```

##### Thanks
Heavily influenced by [amient's hello-kafka-streams demo](https://github.com/amient/hello-kafka-streams)

Lots of help from the [Confluent Kafka Streams examples](https://github.com/confluentinc/examples/tree/master/kafka-streams/src/main/java/io/confluent/examples/streams)
