package actors

import actors.ApiConsumerActor.{BAD_RESPONSE, FetchUrl, FetchedResponse, USER_AGENT}
import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import factories.{ConnectionFactory, IoFactory}
import org.mockito.ArgumentMatchers.any
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

import java.net.HttpURLConnection
import scala.io.Source


class ApiConsumerActorTest extends TestKit(ActorSystem("ApiConsumerActorTest"))
  with AnyWordSpecLike
  with BeforeAndAfterAll
  with ImplicitSender
  with MockitoSugar // when, expect, withObjectMocked, etc.
{
  override protected def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)

  "api consumer" should {
    "call factories and return a result" in {
      val connMock = mock[HttpURLConnection]
      when(connMock.getResponseCode).thenReturn(HttpURLConnection.HTTP_OK)

      val responseString =
        """{
            "latitude":51.04,
            "longitude":13.74,
            "windspeed":5.1,
            "winddirection":356,
            "weathercode":1,
            "is_day":1,
            "time":"2023-09-11T11:00"
          }""".stripMargin
      val sourceMock = mock[Source]
      when(sourceMock.getLines()).thenReturn(Iterator(responseString))

      withObjectMocked[ConnectionFactory.type] {
        withObjectMocked[IoFactory.type] {
          when(ConnectionFactory.connection(any[String])).thenReturn(connMock)
          when(IoFactory.getSource(connMock)).thenReturn(sourceMock)

          val apiConsumer = TestActorRef[ApiConsumerActor]
          apiConsumer ! FetchUrl(ApiConsumerActor.API_ENDPOINT)

          // Expectations on mock!
          verify(connMock).setRequestMethod("GET")
          verify(connMock).setRequestProperty("User-Agent", USER_AGENT)


          val responseMessage = expectMsgType[FetchedResponse]
          assert(responseMessage.data == responseString)
        }
      }
    }
  }

}
