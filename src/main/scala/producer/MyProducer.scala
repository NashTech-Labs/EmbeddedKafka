package producer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


case class User(name:String,id:Int)

class MyProducer{
  def writeToKafka(topic:String) = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "serde.UserSerializer")
    val producer = new KafkaProducer[String,User](props)
    for(i <- 1 to 30) {
      val record = new ProducerRecord[String, User](topic,User("name" + i, i))
      producer.send(record)
    }
  }
}

object ProducerMain extends App{
  val topic = "test-topic"
  (new MyProducer).writeToKafka(topic)
}

