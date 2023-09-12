package actors

import actors.ApiConsumerActor.{API_ENDPOINT, FetchUrl, FetchedResponse}
import actors.MainActor.Start
import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.util.{Failure, Success}

class MainActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case Start =>
      val apiConsumer = context.actorOf(Props[ApiConsumerActor])
      // timeout is used in Future completion
      implicit val timeout: Timeout = Timeout(5 second)

      val consumerFuture: Future[FetchedResponse] =
        (apiConsumer ? FetchUrl(API_ENDPOINT)).asInstanceOf[Future[FetchedResponse]]

      consumerFuture.onComplete {
        case Success(value) => log.debug("Fetched Data: {}", value)
        case Failure(exception) => log.debug("Failure: {}", exception.getMessage)
      }

    case msg => log.debug("Unknown message: {}", msg)
  }
}

object MainActor {
  case object Start
}
