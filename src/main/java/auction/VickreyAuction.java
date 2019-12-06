package auction;

import bidder.BidderAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VickreyAuction extends AbstractAuction {


    private double maximumStartingPrice;

    public VickreyAuction(double maximumStartingPrice, AuctionMonitor monitor) {
        super(monitor);
        this.maximumStartingPrice = maximumStartingPrice;
    }

    public VickreyAuction(double maximumStartingPrice) {
        this.maximumStartingPrice = maximumStartingPrice;
    }

    public AuctionResult runAuction(List<? extends BidderAgent> agents, Seller seller) {
        double startingPrice = seller.getStartingPrice(maximumStartingPrice);

        double[] bids = new double[agents.size()];
        for (int i = 0; i < bids.length; i++) {
            bids[i] = agents.get(i).bid(seller, startingPrice);
        }

        double marketPrice = getMarketPrice(bids);

        //Todo refactor to make this nice
        double highestWinningPrice = -1;
        int highIndex = -1;
        double secondHighestWinningPrice = -1;
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] > marketPrice) {
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

        if(secondHighestWinningPrice < 0){
            secondHighestWinningPrice = (highestWinningPrice + startingPrice) / 2;
        }

        Map<BidderAgent, Double> bidMap = new HashMap<>();
        for (int i = 0; i < bids.length; i++) {
            bidMap.put(agents.get(i), bids[i]);
        }
        return new AuctionResult(agents.get(highIndex), seller, marketPrice, highestWinningPrice, secondHighestWinningPrice, bidMap);
    }


}
