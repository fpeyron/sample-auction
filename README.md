# Sample Algorithm Auction #

This project is a sample implementation of this short specifications :
 
    Let's consider a second-price, sealed-bid auction:
    * An object is for sale with a reserve price.
    * We have several potential buyers, each one being able to place one or more bids.
    * The buyer winning the auction is the one with the highest bid above or equal to the reserve price.
    * The winning price is the highest bid price from a non-winning buyer above the reserve price (or the reserve price if none applies)
      
    __Example__ :
     Consider 5 potential buyers (A, B, C, D, E) who compete to acquire an object with a reserve price set at 100 euros, bidding as follows:
       
      A: 2 bids of 110 and 130 euros
      B: 0 bid
      C: 1 bid of 125 euros
      D: 3 bids of 105, 115 and 90 euros
      E: 3 bids of 132, 135 and 140 euros
       
      The buyer E wins the auction at the price of 130 euros. 


## About the Project
#### Technology ####

* root language  : [Scala 2.12](http://scala-lang.org/)
* test framework : [Scalatest](http://www.scalatest.org/)

#### Implementation ####

Two class :
* **Pid** : class `fr.sysf.sample.Pid`
  > a implementation of the pid.
* **Auction** =  class `fr.sysf.sample.Auction` 
  > a implementation of the auction.
  
* The function `auction.getWinningOffer` : take no parameter return the current winner with the current price for auction `auction`. 

* The function `auction.addPid` : take new Pid, and return new instance of auction with state changed.
 
#### To be Improved ####
 
 1. Add new attribute to describe auction (initial value, start and end date, number of buyer ...)
 
  
## Getting Started

###### Requirements :
* Java [OpenJDK](https://openjdk.java.net/) (version >= 8)
* [Sbt](http://www.scala-sbt.org/) (version >= 1.0.0)

> **NOTE** This project is compatible Java 11.

#### Testing 
```bash
sbt test
```

#### Testing and coverage

```bash
sbt clean coverage test coverageReport
```
The complete coverage report is generated as http page : `./target/scala-2.12/scoverage-report/index.html`.   


#### Check style

```bash
sbt scalastyle
```
Results of checks appear in console.   


## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
