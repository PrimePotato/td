import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyBlender implements PriceBlender {

    Map<MarketSource, Double> sourceBids = new HashMap<>();
    Map<MarketSource, Double> sourceAsks = new HashMap<>();
    private volatile double bestBid;
    private volatile double bestAsk;
    private volatile double bestMid;

    @Override
    public double getBestBid() {
        return bestBid;
    }

    @Override
    public double getBestAsk() {
        return bestAsk;
    }

    @Override
    public double getBestMid() {
        return bestMid;
    }

    @Override
    public synchronized void updatePrice(double bid, double ask, MarketSource source) {

        //Source Cross
        if (bid > ask) {
            sourceAsks.remove(source);
            sourceBids.remove(source);
            return;
        }

        //Zero Bid
        if (bid > 0) {
            sourceBids.put(source, bid);
        } else {
            sourceBids.remove(source);
        }

        //Zero Ask
        if (ask > 0) {
            sourceAsks.put(source, ask);
        } else {
            sourceAsks.remove(source);
        }

        //Resolve best bid and ask.
        if (sourceBids.size() > 0) {
            bestBid = Collections.max(sourceBids.values());
        } else {
            bestBid = 0.;
        }
        if (sourceAsks.size() > 0) {
            bestAsk = Collections.min(sourceAsks.values());
        } else {
            bestAsk = 0.;
        }

        //Handle Price Cross
        if (bestBid > bestAsk) {
            bestBid = (bestBid + bestAsk) / 2.;
            bestAsk = bestBid;
        }

        //Resolve Mid
        if ((bestBid > 0) && (bestAsk > 0)) {
            bestMid = (bestBid + bestAsk) / 2.;
        } else {
            bestMid = 0;
        }

    }

}


