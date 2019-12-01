package bidder;

import auction.AuctionResult;
import auction.Seller;

import java.util.List;

public interface BidderAgent {

    public double bid(Seller seller, double startingPrice);

    void update(AuctionResult result);

    public void startAuction(List<Seller> sellers);
}
