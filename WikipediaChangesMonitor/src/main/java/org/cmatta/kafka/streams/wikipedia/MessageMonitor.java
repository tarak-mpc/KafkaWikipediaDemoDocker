package org.cmatta.kafka.streams.wikipedia;

import io.confluent.examples.streams.utils.SpecificAvroDeserializer;
import io.confluent.examples.streams.utils.SpecificAvroSerde;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.cmatta.kafka.connect.irc.Message;

import java.util.Properties;

/**
 * Created by chris on 10/15/16.
 */
public class MessageMonitor {
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wikipedia-monitor");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka0:9090,kafka1:9091,kafka2:9092");
    props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "zookeeper:2181");
    props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://schema-registry:8081");
    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put("consumer.interceptor.classes", "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
    props.put("producer.interceptor.classes", "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor");

    KStreamBuilder builder = new KStreamBuilder();

    KStream<String, Message> wikipediaRaw = builder.stream("wikipedia.raw");

    wikipediaRaw.map(WikipediaMessageParser::parseMessage)
        .filter((k, v) -> k != null && v != null).to("wikipedia.parsed");

//    TODO Add KTable example code
    final KafkaStreams streams = new KafkaStreams(builder, props);
    streams.start();

    // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));


  }
}
