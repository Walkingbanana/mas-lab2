package bidder;

import auction.AuctionResult;
import auction.Seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MASBidderAgent extends AbstractAgent {
    private static Random random = new Random();

    private Map<Seller, Double> biddingFactors;
    private final double increaseFactor;
    private final double decreaseFactor;


    public MASBidderAgent(double increaseFactor, double decreaseFactor) {
        super();
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
        this.biddingFactors = new HashMap<>();
    }

    public double bid(Seller seller, double startingPrice) {
            return this.biddingFactors.get(seller) * startingPrice;
    }

    public void update(AuctionResult result) {
        Double biddingFactor = this.biddingFactors.get(result.getSeller());
        if (this.equals(result.getWinner()) || result.getBids().get(this) >= result.getMarketPrice()) {
            this.biddingFactors.put(result.getSeller(), biddingFactor * decreaseFactor);
        } else {
            this.biddingFactors.put(result.getSeller(), biddingFactor * increaseFactor);
        }
    }

    public void startAuction(List<Seller> sellers) {
        for (Seller seller : sellers) {
            this.biddingFactors.put(seller, random.nextDouble() * 1337);
        }
    }
}
