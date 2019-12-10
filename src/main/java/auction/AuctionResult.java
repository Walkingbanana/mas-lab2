package auction;

import bidder.BidderAgent;

import java.util.Map;

public class AuctionResult {

    private final BidderAgent winner;
    private final Seller seller;
    private final double marketPrice;
    private final double highestBid;
    private final double paidPrice;
    private final double startingPrice;
    private final Map<BidderAgent, Double> bids;

    public AuctionResult(BidderAgent winner, Seller seller, double startingPrice, double marketPrice, double highestBid, double secondHighestBid, Map<BidderAgent, Double> bids) {
        this.winner = winner;
        this.seller = seller;
        this.marketPrice = marketPrice;
        this.highestBid = highestBid;
        this.paidPrice = secondHighestBid;
        this.bids = bids;
        this.startingPrice = startingPrice;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public Seller getSeller() {
        return seller;
    }

    public BidderAgent getWinner() {
        return winner;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public double getPaidPrice() {
        return paidPrice;
    }

    public Map<BidderAgent, Double> getBids() {
        return bids;
    }
}
