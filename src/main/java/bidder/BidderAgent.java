package bidder;

import auction.AuctionResult;
import auction.Seller;

import java.util.List;

public interface BidderAgent {

    int getAgentID();

    double bid(Seller seller, double startingPrice);

    void update(AuctionResult result);

    void startAuction(List<Seller> sellers);
}
