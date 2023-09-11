package actors

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike


class ApiConsumerActorTest extends TestKit(ActorSystem("ApiConsumerActorTest"))
  with AnyWordSpecLike
  with BeforeAndAfterAll
  with MockitoSugar // when, expect, withObjectMocked, etc.
{
  override protected def afterAll(): Unit =
    TestKit.shutdownActorSystem(system)


}
