package auction;

import auction.monitor.AuctionMonitor;
import auction.monitor.BiddingFactorMonitor;
import bidder.BidderAgent;
import bidder.LeveledBidderAgent;

import java.util.*;

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
        agents.forEach(agent -> agent.startAuction(sellers));

        this.monitor.onRoundAuctionStart();
        for (int i = 0; i < rounds; i++) {
            List<BidderAgent> biddingAgents = new ArrayList<>(agents);
            scenarioResult.startNewRound();

            Collections.shuffle(sellers);
            for (Seller seller : sellers) {

                if (biddingAgents.size() == 0){
                    break;
                }
                AuctionResult result = runAuction(biddingAgents, seller);

                for (BidderAgent agent : biddingAgents) {
                    agent.update(result);
                }

                if (result.getWinner() instanceof LeveledBidderAgent) {
                    ((LeveledBidderAgent) result.getWinner()).resetAuctionRound();
                } else {
                    biddingAgents.remove(result.getWinner());
                }
                scenarioResult.addResult(result);
                monitor.onAuctionEnd(result);
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
