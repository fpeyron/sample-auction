package fr.sysf.sample

/**
  * Implementation of Bid
  *
  * @param buyerId : Id of the buyer
  * @param price : amount for the bid
  */
case class Bid(buyerId: BuyerId, price: Int) {

  require(price > 0, "price must be > 0")
}
