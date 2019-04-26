package fr.sysf.sample

import org.scalatest.WordSpecLike

import scala.util.Random

class BidSpec extends WordSpecLike {

  "constructor" should {
    "successes with positive price" in {
      val myPrice = Random.nextInt(100) + 1
      assertResult(myPrice)(Bid(Random.nextString(5), myPrice).price)
    }

    "fails with negative price" in {
      val myPrice = -Random.nextInt(100) - 1
      assertThrows[IllegalArgumentException](Bid(Random.nextString(5), myPrice))
    }
  }
}
