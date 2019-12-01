package auction;

import bidder.BidderAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAuction implements Auction {

    public AuctionScenarioResult runAuctionRounds(int rounds, List<Seller> sellers, List<BidderAgent> agents) {
        AuctionScenarioResult scenarioResult = new AuctionScenarioResult(agents);
        agents.forEach(agent -> agent.startAuction(sellers));

        for (int i = 0; i < rounds; i++) {
            List<BidderAgent> biddingAgents = new ArrayList<>(agents);
            scenarioResult.startNewRound();

            Collections.shuffle(sellers);
            for (Seller seller : sellers) {
                AuctionResult result = runAuction(biddingAgents, seller);

                for (BidderAgent agent : biddingAgents) {
                    agent.update(result);
                }
                biddingAgents.remove(result.getWinner());
                scenarioResult.addResult(result);
            }
        }
        return scenarioResult;
    }


    double getMarketPrice(double[] bids) {
        double sumBids = 0;
        for (double bid : bids) {
            sumBids += bid;
        }
        return sumBids / bids.length;
    }
}
