package auction;

import bidder.BidderAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeveledAuctionScenarioResult extends AuctionScenarioResult {


    public LeveledAuctionScenarioResult(List<? extends BidderAgent> agents) {
        super(agents);
    }

    @Override
    protected Map<Seller, Double> calcSellerProfits() {
        Map<Seller, Double> sellerProfits = new HashMap<>();

        for (List<AuctionResult> results : roundAuctionResults) {
            for (AuctionResult result : results) {
                Double profit = sellerProfits.computeIfAbsent(result.getSeller(), k -> 0d);
                profit += result.getPaidPrice();
                sellerProfits.put(result.getSeller(), profit);
            }
        }
        return sellerProfits;
    }

    @Override
    protected Map<BidderAgent, Double> calcBuyerProfits() {
        Map<BidderAgent, Double> resultMap = new HashMap<>();

        for (BidderAgent agent : scenarioResult.keySet()) {
            double profit = 0;
            List<AuctionResult> results = scenarioResult.get(agent);
            for (AuctionResult result : results) {
                profit += result.getMarketPrice() - result.getPaidPrice();
            }
            resultMap.put(agent, profit);
        }
        return resultMap;
    }
}
