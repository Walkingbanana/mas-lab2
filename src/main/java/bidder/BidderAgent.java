package bidder;

import auction.AuctionResult;
import auction.Seller;

import java.util.List;
import java.util.Map;

public interface BidderAgent {

    int getAgentID();

    double bid(Seller seller, double startingPrice);

    void update(AuctionResult result);

    void startAuction(List<Seller> sellers);

    Map<Seller, Double> getBiddingFactors();
}
