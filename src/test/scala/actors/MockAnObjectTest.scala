package actors

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.mockito.MockitoSugar
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

object FooObject {
  def foo: String = "not mocked"
}

class MockAnObjectTest extends AnyWordSpecLike
  with MockitoSugar // when, expect, withObjectMocked, etc.
{

  /**
   * To mock scala objects it's mandatory to create file
   * `src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker`
   * containing a single line `mock-maker-inline`
   * https://github.com/mockito/mockito-scala#mocking-scala-object
   */

  "mock" must  {
    "stub an object method" in {
      FooObject.foo shouldBe "not mocked"

      withObjectMocked[FooObject.type]{
        when(FooObject.foo) thenReturn "mocked"
        FooObject.foo shouldBe "mocked"
      }

      FooObject.foo shouldBe "not mocked"
    }
  }

}
