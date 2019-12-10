package bidder;

import auction.AuctionResult;
import auction.Seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class AbstractAgent implements BidderAgent {

    private static int nextAgentID = 0;
    private static final Random random = new Random();

    private final int agentID;
    private final double increaseFactor;
    private final double decreaseFactor;

    Map<Seller, Double> biddingFactors;

    public AbstractAgent(double increaseFactor, double decreaseFactor) {
        this.agentID = nextAgentID;
        nextAgentID++;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
        this.biddingFactors = new HashMap<>();
    }

    public int getAgentID() {
        return agentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAgent that = (AbstractAgent) o;
        return agentID == that.agentID;
    }

    @Override
    public int hashCode() {
        return getAgentID();
    }

    public double bid(Seller seller, double startingPrice) {
        return this.biddingFactors.get(seller) * startingPrice;
    }

    public void update(AuctionResult result) {
        Double biddingFactor = this.biddingFactors.get(result.getSeller());
        if (this.equals(result.getWinner()) || result.getBids().get(this) >= result.getMarketPrice()) {
            double newBiddingFactor = biddingFactor * decreaseFactor;
            if (newBiddingFactor < 1)
                newBiddingFactor = 1;
            this.biddingFactors.put(result.getSeller(), newBiddingFactor);
        } else {
            this.biddingFactors.put(result.getSeller(), biddingFactor * increaseFactor);
        }
    }

    public void startAuction(List<Seller> sellers) {
        biddingFactors = new HashMap<>();
        for (Seller seller : sellers) {
            this.biddingFactors.put(seller, random.nextDouble() + 1);
        }
    }

    @Override
    public  Map<Seller, Double> getBiddingFactors() {
        return biddingFactors;
    }
}
