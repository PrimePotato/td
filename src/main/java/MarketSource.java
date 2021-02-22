import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MarketSource {
    SOURCE_A, SOURCE_B, SOURCE_C;

    private static final List<MarketSource> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static MarketSource randomSource() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
