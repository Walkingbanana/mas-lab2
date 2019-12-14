package auction;

import auction.monitor.AuctionMonitor;
import auction.monitor.BiddingFactorMonitor;
import bidder.BidderAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class VickreyAuction extends AbstractAuction
{

    public VickreyAuction(double maximumStartingPrice, AuctionMonitor monitor) {
        super(monitor, maximumStartingPrice);
    }

    public VickreyAuction(double maximumStartingPrice) {
        super(maximumStartingPrice);
    }

    public AuctionResult runAuction(List<? extends BidderAgent> agents, Seller seller) {
        double startingPrice = seller.getStartingPrice(maximumStartingPrice);

        double[] bids = new double[agents.size()];
        for (int i = 0; i < bids.length; i++) {
            bids[i] = (agents.get(i).bid(seller, startingPrice));
        }

        double marketPrice = getMarketPrice(bids);

        //Todo refactor to make this nice
        double highestWinningPrice = -1;
        int highIndex = ThreadLocalRandom.current().nextInt(0, agents.size()); // Choose a random agent -> fixes an issue when all the bids are the same
        double secondHighestWinningPrice = -1;
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] > marketPrice || bids[i] < 0) {
                continue;
            }
            if (bids[i] > highestWinningPrice) {
                secondHighestWinningPrice = highestWinningPrice;
                highestWinningPrice = bids[i];
                highIndex = i;
            } else if (bids[i] > secondHighestWinningPrice) {
                secondHighestWinningPrice = bids[i];
            }
        }

        if (secondHighestWinningPrice < 0) {
            secondHighestWinningPrice = (highestWinningPrice + startingPrice) / 2;
        }

        Map<BidderAgent, Double> bidMap = new HashMap<>();
        for (int i = 0; i < bids.length; i++) {
//            if(bids[i] < 0){
//                continue;
//            }
            bidMap.put(agents.get(i), bids[i]);
        }
        return new AuctionResult(agents.get(highIndex), seller, startingPrice, marketPrice, highestWinningPrice, secondHighestWinningPrice, bidMap);
    }

}
