package consumer
import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import producer.User

import scala.collection.JavaConverters._

class MyConsumer {

  def consumeFromKafka(topic: String) = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "serde.UserDeserializer")
    props.put("group.id", "something")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val consumer: KafkaConsumer[String, User] = new KafkaConsumer[String, User](props)
    consumer.subscribe(util.Collections.singletonList(topic))
    val record = consumer.poll(5000).asScala.toList.map(_.value())
    record
  }
}


object ConsumerMain extends App {
  val topic = "test-topic"
  (new MyConsumer).consumeFromKafka(topic)
}
