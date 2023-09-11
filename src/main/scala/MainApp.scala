import actors.MainActor
import actors.MainActor.Start
import akka.actor.{ActorSystem, Props}
import com.sun.org.slf4j.internal.LoggerFactory


object MainApp extends App {

  val log = LoggerFactory.getLogger(getClass)

  val system = ActorSystem("MainApp")

  val mainActor = system.actorOf(Props[MainActor])

  mainActor ! Start

}
