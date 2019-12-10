package auction;

import auction.Seller;
import bidder.BidderAgent;

import java.util.List;
import java.util.Map;

public class ScenarioStatistics {

    private Map<Seller, Double> sellerProfits;
    private Map<Seller, List<Double>> marketPriceDevelopment;
    private Map<BidderAgent, Double> buyerProfits;

    public ScenarioStatistics() {
    }

    public void setSellerProfits(Map<Seller, Double> sellerProfits) {
        this.sellerProfits = sellerProfits;
    }

    public void setMarketPriceDevelopment(Map<Seller, List<Double>> marketPriceDevelopment) {
        this.marketPriceDevelopment = marketPriceDevelopment;
    }

    public void setBuyerProfits(Map<BidderAgent, Double> buyerProfits) {
        this.buyerProfits = buyerProfits;
    }

    public Map<Seller, Double> getSellerProfits() {
        return sellerProfits;
    }

    public Map<Seller, List<Double>> getMarketPriceDevelopment() {
        return marketPriceDevelopment;
    }

    public Map<BidderAgent, Double> getBuyerProfits() {
        return buyerProfits;
    }
}
