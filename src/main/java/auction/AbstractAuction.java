package auction;

import bidder.BidderAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractAuction implements Auction {
    private AuctionMonitor monitor;
    public AbstractAuction(AuctionMonitor monitor)
    {
        this.monitor = monitor;
    }

    public AbstractAuction()
    {
        // Of course java is not able to fucking handle default values in constructors, who the fuck needs that shit anyway
        this(null);
    }

    public AuctionScenarioResult runAuctionRounds(int rounds, List<Seller> sellers, List<? extends BidderAgent> agents) {
        AuctionScenarioResult scenarioResult = new AuctionScenarioResult(agents);
        agents.forEach(agent -> agent.startAuction(sellers));

        monitor.onRoundAuctionStart();
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
