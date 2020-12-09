package services

import org.scalatestplus.play.PlaySpec

class DummyMLServiceTest extends PlaySpec {
    "FakeMLServiceTest " must {
      "getCustomerGroup " in {
        val group = new DummyMLService().getConfidenceLevel("1985-09-06")
        group mustEqual("high")
      }
    }
}
