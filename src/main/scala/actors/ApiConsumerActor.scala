package actors

import actors.ApiConsumerActor.{BAD_RESPONSE, FetchUrl, FetchedResponse, getResponse}
import akka.actor.{Actor, ActorLogging}
import factories.{ConnectionFactory, IoFactory}

import java.net.HttpURLConnection
import scala.util.Try

class ApiConsumerActor extends Actor with ActorLogging {
  override def preStart(): Unit = {
    log.debug("Starting")
    super.preStart()
  }

  override def receive: Receive = {
    case FetchUrl(url: String) =>
      val conn = ConnectionFactory.connection(url)
      val response: String = getResponse(conn).getOrElse(BAD_RESPONSE)
      log.debug(response)

      context.sender() ! FetchedResponse(response)
  }
}

object ApiConsumerActor {
  final val API_ENDPOINT = "https://api.open-meteo.com/v1/forecast?latitude=51.05&longitude=13.74&current_weather=true"
  final val USER_AGENT = "Mozilla/5.0"
  final val BAD_RESPONSE = "bad response :/"

  case class FetchUrl(url: String)

  case class FetchedResponse(data: String)

  def getResponse(conn: HttpURLConnection): Option[String] = {
    conn.setRequestMethod("GET")
    conn.setRequestProperty("User-Agent", USER_AGENT)

    conn.getResponseCode match {
      case HttpURLConnection.HTTP_OK =>
        Try(IoFactory.getSource(conn).getLines().mkString("|")).toOption
      case _ => None
    }
  }

  def parseJson(jsonString: String): Map[String, Float] = {
    upickle.default.read[Map[String, Float]](jsonString)
  }
}