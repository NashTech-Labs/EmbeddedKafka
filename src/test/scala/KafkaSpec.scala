import consumer.MyConsumer
import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import producer.{MyProducer, User}
import serde.UserSerializer

class KafkaSpec extends FlatSpec with EmbeddedKafka with BeforeAndAfterAll {
  val topic = "test-topic"
  val producer = new MyProducer
  val consumer = new MyConsumer
  implicit val config = EmbeddedKafkaConfig(kafkaPort = 9092, zooKeeperPort = 2181)

  override def beforeAll(): Unit = {
    EmbeddedKafka.start()
  }

  it should "publish synchronously data to kafka" in {
    producer.writeToKafka(topic)
    val response = consumeFirstStringMessageFrom(topic)
    assert(Some(response).isDefined)
  }

  it should "consume message from published topic" in {
    implicit val serializer = new UserSerializer
    val list = List(User("name1",1),User("name2",2),User("name3",3),User("name4",4),User("name5",5),User("name6",6))
    list.foreach(publishToKafka(topic,_))
    // producer.writeToKafka(topic)
    val response = consumer.consumeFromKafka(topic)
    assert(response.size > 6)
  }

  override def afterAll(): Unit = {
    EmbeddedKafka.stop()
  }
}


