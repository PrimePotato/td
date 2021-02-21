import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyBlender implements PriceBlender {

    Map<MarketSource, Double> sourceBids = new HashMap<>();
    Map<MarketSource, Double> sourceAsks = new HashMap<>();

    @Override
    public double getBestBid() {
        if (sourceBids.size() > 0) {
            return Collections.max(sourceBids.values());
        } else return 0;
    }

    @Override
    public double getBestAsk() {
        if (sourceAsks.size() > 0) {
            return Collections.min(sourceAsks.values());
        } else return 0;
    }

    @Override
    public double getBestMid() {
        double b = getBestBid();
        double a = getBestAsk();
        if (a == 0) return 0;
        if (b == 0) return 0;
        return (a + b) / 2;
    }

    @Override
    public synchronized void updatePrice(double bid, double ask, MarketSource source) {
        if (bid > ask) {
            sourceAsks.remove(source);
            sourceBids.remove(source);
            return;
        }
        if (bid > 0) {
            sourceBids.put(source, bid);
        } else {
            sourceBids.remove(source);
        }
        if (ask > 0) {
            sourceAsks.put(source, ask);
        } else {
            sourceAsks.remove(source);
        }
    }

}


