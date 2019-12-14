package auction;

import auction.monitor.AuctionMonitor;
import bidder.BidderAgent;
import bidder.LeveledBidderAgent;
import bidder.MASBidderAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAuction implements Auction {

    private AuctionMonitor monitor;
    double maximumStartingPrice;

    public AbstractAuction(AuctionMonitor monitor, double maximumStartingPrice) {
        this.monitor = monitor;
        this.maximumStartingPrice = maximumStartingPrice;
    }

    public AbstractAuction(double maximumStartingPrice) {
        // Of course java is not able to fucking handle default values in constructors, who the fuck needs that shit anyway
        this(null, maximumStartingPrice);
    }

    public AuctionScenarioResult runAuctionRounds(int rounds, List<Seller> sellers, List<? extends BidderAgent> agents) {
        AuctionScenarioResult scenarioResult = new AuctionScenarioResult(agents);
        List<AuctionResult> resultsForCurrentRound = new ArrayList<>(sellers.size());
        agents.forEach(agent -> agent.startAuction(sellers));

        this.monitor.onRoundAuctionStart();
        for (int i = 0; i < rounds; i++) {
            List<BidderAgent> biddingAgents = new ArrayList<>(agents);
            scenarioResult.startNewRound();

            Collections.shuffle(sellers);
            for (Seller seller : sellers) {

                if (biddingAgents.size() == 0) {
                    break;
                }
                AuctionResult result = runAuction(biddingAgents, seller);

                for (BidderAgent agent : biddingAgents) {
                    agent.update(result);
                }

                if (result.getWinner() instanceof MASBidderAgent) {
                    biddingAgents.remove(result.getWinner());
                }
                scenarioResult.addResult(result);
                monitor.onAuctionEnd(result);
            }

            for (BidderAgent agent : agents) {
                if (agent instanceof LeveledBidderAgent) {
                    ((LeveledBidderAgent) agent).resetAuctionRound();
                }
            }
        }


//        for (AuctionResult result : scenarioResult.)
//
//            ((LeveledBidderAgent) result.getWinner()).resetAuctionRound();
        return scenarioResult;
    }

    double getMarketPrice(double[] bids) {
        double sumBids = 0;
        int nonBidders = 0;
        for (double bid : bids) {
            if(bid < 0){
                nonBidders++;
                continue;
            }
            sumBids += bid;
        }
        return sumBids / bids.length-nonBidders;
    }

}
