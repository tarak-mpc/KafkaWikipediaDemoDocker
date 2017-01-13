package io.confluent.examples.kafka.streams.twitter;

import io.confluent.examples.kafka.connect.twitter.Status;
import io.confluent.examples.kafka.streams.utils.SpecificAvroSerde;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;


import java.util.Properties;


public class StatusMonitor {
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-twitter-monitor");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka0:9090,kafka1:9091,kafka2:9092");
    props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper:2181");
    props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put("consumer.interceptor.classes", "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
    props.put("producer.interceptor.classes", "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor");

    KStreamBuilder builder = new KStreamBuilder();

    KStream<String, Status> twitterRaw = builder.stream("twitter.raw");

    twitterRaw.map(TwitterStatusParser::parseStatus)
        .filter((k, v) -> k != null && v != null).to("twitter.parsed");

//    TODO Add KTable example code
    final KafkaStreams streams = new KafkaStreams(builder, props);
    streams.start();

    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));


  }
}
