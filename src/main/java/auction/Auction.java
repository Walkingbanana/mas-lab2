package auction;

import bidder.BidderAgent;

import java.util.List;

public interface Auction {

    public AuctionResult runAuction(List<BidderAgent> agents, Seller seller);

    public AuctionScenarioResult runAuctionRounds(int rounds, List<Seller> sellers, List<BidderAgent> agents);

}
