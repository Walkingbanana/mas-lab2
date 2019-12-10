package auction;

import bidder.AbstractAgent;

import java.util.Random;

public class RandomSeller implements Seller {

    private static final Random random = new Random(1337);

    private static int nextSellerID = 0;

    private int sellerID;

    public RandomSeller() {
        this.sellerID = nextSellerID;
        nextSellerID++;
    }

    public int getSellerID() {
        return sellerID;
    }

    @Override
    public double getStartingPrice(double max)
    {
        return random.nextDouble() * max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomSeller that = (RandomSeller) o;
        return sellerID == that.sellerID;
    }

    @Override
    public int hashCode() {
        return getSellerID();
    }
}
