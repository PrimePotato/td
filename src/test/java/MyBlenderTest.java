import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyBlenderTest {

    private final PriceBlender pb = new MyBlender();
    private final double TOL = 1e-16;

    @BeforeEach
    void setUp() {
        PriceBlender pb = new MyBlender();
    }

    @Test
    void getBestBid() {
        pb.updatePrice(0., 10., MarketSource.SOURCE_A);
        pb.updatePrice(10., 0., MarketSource.SOURCE_B);
        pb.updatePrice(5., 11., MarketSource.SOURCE_C);
        Assertions.assertEquals(pb.getBestBid(), 5., TOL);
    }

    @Test
    void getBestAsk() {
        pb.updatePrice(0., 10., MarketSource.SOURCE_A);
        pb.updatePrice(10., 0., MarketSource.SOURCE_B);
        pb.updatePrice(5., 11., MarketSource.SOURCE_C);
        Assertions.assertEquals(pb.getBestAsk(), 10., TOL);
    }

    @Test
    void getBestMid() {
        pb.updatePrice(0., 10., MarketSource.SOURCE_A);
        pb.updatePrice(10., 0., MarketSource.SOURCE_B);
        pb.updatePrice(5., 11., MarketSource.SOURCE_C);
        Assertions.assertEquals(pb.getBestMid(), 7.5, TOL);
    }

    @Test
    void updatePrice() {
        pb.updatePrice(9., 10., MarketSource.SOURCE_A);
        Assertions.assertEquals(pb.getBestMid(), 9.5, TOL);
        pb.updatePrice(0, 10., MarketSource.SOURCE_A);
        Assertions.assertEquals(pb.getBestMid(), 0, TOL);
        pb.updatePrice(9., 0, MarketSource.SOURCE_A);
        Assertions.assertEquals(pb.getBestMid(), 0, TOL);

    }


}