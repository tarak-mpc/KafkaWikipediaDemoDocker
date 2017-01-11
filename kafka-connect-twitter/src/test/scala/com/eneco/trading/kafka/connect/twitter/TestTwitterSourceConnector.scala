package com.eneco.trading.kafka.connect.twitter

import org.apache.kafka.connect.errors.ConnectException

import scala.collection.JavaConverters._

/**
  * Created by andrew@datamountaineer.com on 29/02/16. 
  * kafka-connect-twitter
  */
class TestTwitterSourceConnector extends TestTwitterBase {
  val goodProps = getConfig
  val badProps = goodProps + (TwitterSourceConfig.BATCH_SIZE -> "this is no integer")

  test("A TwitterSourceConnector should start with valid properties") {
    val t = new TwitterSourceConnector()
    t.start(goodProps.asJava)
  }

  test("A TwitterSourceConnector shouldn't start with invalid properties") {
    val t = new TwitterSourceConnector()
    a[ConnectException] should be thrownBy {
      t.start(badProps.asJava)
    }
  }

  test("A TwitterSourceConnector should provide the correct taskClass") {
    val t = new TwitterSourceConnector()
    t.taskClass() should be (classOf[TwitterSourceTask])
  }

  test("A TwitterSourceConnector should return a taskConfig for each task") {
    val t = new TwitterSourceConnector()
    t.taskConfigs(42).size() should be (42)
  }
}
