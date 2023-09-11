package actors

import akka.actor.{Actor, ActorLogging}

class DataPersistenceActor extends Actor with ActorLogging {


  override def preStart(): Unit = {
    log.debug("Starting")
    super.preStart()
  }


  override def receive: Receive = Actor.emptyBehavior
}
