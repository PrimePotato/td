# TD Exercise

## Directory Structure
The project uses the standard Maven directory structure.

## Notable Tests
* `partialPrice` contains a test to deal with a zero bid/ask.
* `priceCross` test showing handling of zero prices. 

## Assumptions
* Prices are positive real numbers.
* If a price presents an arbitrage, the entire update from that source is rejected and ignored.
* If a partial price is received, the non-zero (bid/ask) is still updated.
* If there is a price cross but not directly from the source the bid, mid and ask are all set to be the average of bid and ask. 





