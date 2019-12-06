package auction;

import bidder.BidderAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionScenarioResult {

    private Map<BidderAgent, List<AuctionResult>> scenarioResult;
    private List<List<AuctionResult>> roundAuctionResults;
    private List<AuctionResult> currentRound;

    public AuctionScenarioResult(List<? extends BidderAgent> agents) {
        if (agents == null) {
            throw new IllegalArgumentException();
        }

        this.scenarioResult = new HashMap<>(agents.size());
        for (BidderAgent agent : agents) {
            this.scenarioResult.put(agent, new ArrayList<AuctionResult>());
        }
        this.roundAuctionResults = new ArrayList<>();
    }

    public void addResult(AuctionResult result) {
        List<AuctionResult> results = scenarioResult.get(result.getWinner());
        results.add(result);
        currentRound.add(result);
    }

    public Map<BidderAgent, List<AuctionResult>> getScenarioResult() {
        return scenarioResult;
    }

    public void startNewRound() {
        this.currentRound = new ArrayList<>();
        this.roundAuctionResults.add(currentRound);
    }

    public ScenarioStatistics calculateStatistics() {
        ScenarioStatistics stat = new ScenarioStatistics();


        stat.setBuyerProfits(calcBuyerProfits());
        stat.setMarketPriceDevelopment(calcMarketPriceDevelopment());
        stat.setSellerProfits(calcSellerProfits());

        return stat;
    }

    private Map<Seller, Double> calcSellerProfits() {
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

    private Map<Seller, List<Double>> calcMarketPriceDevelopment() {
        Map<Seller, List<Double>> sellerPrices = new HashMap<>();
        for (List<AuctionResult> results : roundAuctionResults) {
            for (AuctionResult result : results) {
                List<Double> prices = sellerPrices.computeIfAbsent(result.getSeller(), k -> new ArrayList<>());
                prices.add(result.getMarketPrice());
            }
        }
        return sellerPrices;
    }

    private Map<BidderAgent, Double> calcBuyerProfits() {
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
