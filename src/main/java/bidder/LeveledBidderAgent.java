package bidder;

import auction.AuctionResult;
import auction.Seller;

public class LeveledBidderAgent extends AbstractAgent {

    private AuctionResult wonAuction = null;
    private final double anullmentFeeFactor;

    public LeveledBidderAgent(double increaseFactor, double decreaseFactor, double anullmentFeeFactor) {
        super(increaseFactor, decreaseFactor);
        this.anullmentFeeFactor = anullmentFeeFactor;
    }

    @Override
    public double bid(Seller seller, double startingPrice) {
        double bid = super.bid(seller, startingPrice);
        if (wonAuction != null) {
            bid -= (wonAuction.getMarketPrice() - wonAuction.getPaidPrice()) + (anullmentFeeFactor * wonAuction.getPaidPrice());
        }
        if (bid < startingPrice) {
            return startingPrice;
        }
        return bid;
    }

    @Override
    public void update(AuctionResult result) {
        super.update(result);
        if (this.equals(result.getWinner())) {
            wonAuction = result;
        }
    }

    public void resetAuctionRound() {
        this.wonAuction = null;
    }
}
