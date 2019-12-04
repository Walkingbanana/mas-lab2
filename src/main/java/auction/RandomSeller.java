package auction;

import java.util.Random;

public class RandomSeller implements Seller {

    private static final Random random = new Random(1337);

    @Override
    public double getStartingPrice(double max)
    {
        return random.nextDouble() * max;
    }
}
