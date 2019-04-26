package fr.sysf.sample
import org.scalatest.WordSpecLike

import scala.util.Random

class AuctionSpec extends WordSpecLike {

  "constructor" should {
    "successes with positive price (reserve price)" in {
      val price = Random.nextInt(100) + 1
      assertResult(price)(Auction(price).currentPrice)
    }

    "fails with negative price (reserve price)" in {
      val price = -Random.nextInt(100) - 1
      assertThrows[IllegalArgumentException](Auction(price))
    }
  }

  "getWinnerOffer" should {
    "None when best bid is empty" in {
      val auction = Auction(currentPrice = Random.nextInt(2000) + 2)
      assert(auction.getWinningOffer.isEmpty)
    }

    "last when best bid exists" in {
      val currentPrice     = Random.nextInt(2000) + 2
      val buyerId: BuyerId = Random.nextString(5)
      val auction          = Auction(currentPrice = currentPrice, bestBid = Some(Bid(buyerId, currentPrice + 100)))
      assertResult(Some(Bid(buyerId, currentPrice)))(auction.getWinningOffer)
    }
  }

  "addBid" when {
    val auction = Auction(currentPrice = Random.nextInt(2000) + 2)

    "Best bid is empty" should {
      "same auction when the current price is not reached" in {
        val newBid = Bid(Random.nextString(5), auction.currentPrice - 1)

        assertResult(auction)(auction.addBid(newBid))
      }
      "winning auction when the current price is just reached" in {
        val newBid = Bid(Random.nextString(5), auction.currentPrice)
        assertResult(auction.copy(bestBid = Some(newBid)))(auction.addBid(newBid))
      }
      "winning auction when the current price is exceeded" in {
        val newBid = Bid(Random.nextString(5), auction.currentPrice + 10)
        assertResult(auction.copy(bestBid = Some(newBid)))(auction.addBid(newBid))
      }
    }

    "Best big has same buyer" should {
      val currentPrice = Random.nextInt(2000) + 2
      val auction      = Auction(currentPrice = currentPrice, bestBid = Some(Bid("last", currentPrice + 2)))

      "same auction when the current price is not reached" in {
        val newBid = Bid("last", currentPrice - 1)
        assertResult(auction)(auction.addBid(newBid))
      }
      "the same auction when the highest price is not reached" in {
        val newBid = Bid("last", currentPrice + 1)
        assertResult(auction)(auction.addBid(newBid))
      }
      "winning auction when the highest price is exceeded" in {
        val newBid = Bid("last", currentPrice + 3)
        assertResult(auction.copy(bestBid = Some(newBid)))(auction.addBid(newBid))
      }
    }

    "winning big has other buyer" should {
      val currentPrice = Random.nextInt(2000) + 2
      val auction      = Auction(currentPrice = currentPrice, bestBid = Some(Bid("last", currentPrice + 2)))

      "same auction when the current price is not reached" in {
        val newBid = Bid("other", currentPrice - 1)
        assertResult(auction)(auction.addBid(newBid))
      }
      "new current price when the highest price is not reached" in {
        val newBid = Bid("other", currentPrice + 1)
        assertResult(auction.copy(currentPrice = newBid.price))(auction.addBid(newBid))
      }
      "winning auction when the highest price is exceeded" in {
        val newBid = Bid("other", currentPrice + 3)
        assertResult(auction.copy(currentPrice = auction.bestBid.get.price, bestBid = Some(newBid)))(auction.addBid(newBid))
      }
    }
  }

  "check for given example" should {

    val listOfBids: Seq[Bid] = Seq(("A", Seq(110, 130)), ("B", Seq()), ("C", Seq(125)), ("D", Seq(105, 115, 90)), ("E", Seq(132, 135, 140)))
      .flatMap(b ⇒ b._2.map(Bid(b._1, _)))

    val theWinner      = Bid("E", 130)
    val initialAuction = Auction(100)

    "same winner with given order" in {
      assertResult(Some(theWinner))(listOfBids.foldLeft(initialAuction)((a, b) ⇒ a.addBid(b)).getWinningOffer)
    }
    "same winner with reverse order" in {
      assertResult(Some(theWinner))(listOfBids.reverse.foldLeft(initialAuction)((a, b) ⇒ a.addBid(b)).getWinningOffer)
    }
    "same winner with random order" in {
      assertResult(Some(theWinner))(Random.shuffle(listOfBids).foldLeft(initialAuction)((a, b) ⇒ a.addBid(b)).getWinningOffer)
    }
  }
}
