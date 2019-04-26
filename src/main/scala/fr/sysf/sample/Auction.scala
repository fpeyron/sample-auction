package fr.sysf.sample

/**
  * Implementation of Auction
  *
  * @param currentPrice : initial price value (minimum to win)
  * @param bestBid : best Bid receive
  */
case class Auction(currentPrice: Int, bestBid: Option[Bid] = None) {

  require(currentPrice > 0, "currentPrice must be > 0")

  /** Get the winner offer : (best buyer with current price).
    *  If nobody reach the initial amount (the reserve price), it returns None.
    */
  def getWinningOffer: Option[Bid] = bestBid.map(_.copy(price = currentPrice))

  /** Return new Auction after the new Bid is processing.
    *  All business rules are implemented as case statement.
    */
  def addBid(bid: Bid): Auction = bid match {

    // Current price is reached for the first time
    case bid: Bid if bestBid.isEmpty && bid.price >= currentPrice ⇒
      copy(bestBid = Some(bid))

    // Best bid is exceeded by the same buyer
    case bid: Bid if bestBid.exists(b ⇒ b.price < bid.price && b.buyerId == bid.buyerId) ⇒
      copy(bestBid = Some(bid))

    // Current price is exceeded by an other buyer (but best bid is not exceeded)
    case bid: Bid if bid.price > currentPrice && bestBid.exists(b ⇒ b.price >= bid.price && b.buyerId != bid.buyerId) ⇒
      copy(currentPrice = bid.price)

    // Best bid is exceeded by an other buyer
    case bid: Bid if bestBid.exists(_.price < bid.price) ⇒
      copy(currentPrice = bestBid.get.price, bestBid = Some(bid))

    // Current price is not reached
    case _ ⇒
      this
  }
}
