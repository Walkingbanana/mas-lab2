import auction.*;
import bidder.BidderAgent;
import bidder.MASBidderAgent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


class RunAuction {

    @Test
    public void testRunAuction(){
        List<BidderAgent> agents = new ArrayList<>();
        agents.add(new MASBidderAgent(1.1,0.95));
        agents.add(new MASBidderAgent(1.1,0.95));
        agents.add(new MASBidderAgent(1.1,0.95));
        agents.add(new MASBidderAgent(1.1,0.95));

        List<Seller> sellers = new ArrayList<>();
        sellers.add(new RandomSeller());
        sellers.add(new RandomSeller());

        Auction auction = new VickreyAuction(1000);
        AuctionScenarioResult auctionScenarioResult = auction.runAuctionRounds(1000, sellers, agents);

        auctionScenarioResult.calculateStatistics();
    }

}