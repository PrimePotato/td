public interface PriceBlender {

    double getBestBid();


    double getBestAsk();

    //one sided/empty prices should be return 0
    double getBestMid();

    //handle price update from a source, if bid/ask value is 0, it means there is                //no price for it
    void updatePrice(double bid, double ask,  MarketSource source);
}


