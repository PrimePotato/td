import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class MyBlenderTest {

    private final PriceBlender pb = new MyBlender();
    private final double TOL = 1e-16;
    private static final long MEGABYTE = 1024L * 1024L;

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
    void partialPrice() {
        pb.updatePrice(9., 10., MarketSource.SOURCE_A);
        Assertions.assertEquals(9.5, pb.getBestMid(), TOL);
        pb.updatePrice(0, 10., MarketSource.SOURCE_A);
        Assertions.assertEquals(0., pb.getBestMid(), TOL);
        pb.updatePrice(9., 0, MarketSource.SOURCE_A);
        Assertions.assertEquals(0, pb.getBestMid(), TOL);
    }

    @Test
    void priceCross() {
        pb.updatePrice(5, 15., MarketSource.SOURCE_A);
        Assertions.assertEquals(10., pb.getBestMid(), TOL);
        pb.updatePrice(8., 10., MarketSource.SOURCE_B);
        Assertions.assertEquals(9., pb.getBestMid(), TOL);
        pb.updatePrice(11., 10., MarketSource.SOURCE_B);
        Assertions.assertEquals(9., pb.getBestMid(), TOL);

        pb.updatePrice(9., 10., MarketSource.SOURCE_B);
        pb.updatePrice(11., 12., MarketSource.SOURCE_C);
        Assertions.assertEquals(10.5, pb.getBestMid(), TOL);
    }

    @Test
    void visualMemoryTest() {

        System.gc();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        System.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        double p = Math.random();
        IntStream.range(0, 10000000).forEach(x -> pb.updatePrice(p * .9, p, MarketSource.SOURCE_A));

        System.gc();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Used memory is bytes: " + (memoryAfter - memoryBefore));
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memoryAfter - memoryBefore));
    }

    @Test
    void concurrencyTest() {
        Thread t1 = new Thread(() -> pb.updatePrice(10., 12., MarketSource.SOURCE_A));
        t1.run();
        Thread t2 = new Thread(() -> pb.updatePrice(9., 11., MarketSource.SOURCE_B));
        t2.run();
        Thread t3 = new Thread(() -> pb.updatePrice(8., 10., MarketSource.SOURCE_A));
        t3.run();
        Assertions.assertEquals(9.5, pb.getBestMid());
    }


    private static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }


}