package bidder;

import auction.Seller;

import java.util.HashMap;
import java.util.Map;

public class MASBidderAgent extends AbstractAgent {

    public MASBidderAgent(double increaseFactor, double decreaseFactor) {
        super(increaseFactor, decreaseFactor);
        this.biddingFactors = new HashMap<>();
    }

    public double bid(Seller seller, double startingPrice) {
        return this.biddingFactors.get(seller) * startingPrice;
    }


    public Map<Seller, Double> getBiddingFactors() {
        return biddingFactors;
    }
}
