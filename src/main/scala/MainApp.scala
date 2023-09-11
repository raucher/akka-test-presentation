import actors.{ApiConsumerActor, DataPersistenceActor}
import akka.actor.{ActorSystem, Props}

object MainApp extends App{

  val system = ActorSystem("MainApp")

  val apiConsumer = system.actorOf(Props[ApiConsumerActor])
  val dataPersistence = system.actorOf(Props[DataPersistenceActor])



}
