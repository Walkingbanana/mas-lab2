package bidder;

import auction.AuctionResult;
import auction.Seller;

public class LeveledBidderAgent extends AbstractAgent {

    private AuctionResult wonAuction = null;
    private final double anullmentFee;

    public LeveledBidderAgent(double increaseFactor, double decreaseFactor, double anullmentFee) {
        super(increaseFactor, decreaseFactor);
        this.anullmentFee = anullmentFee;
    }

    @Override
    public double bid(Seller seller, double startingPrice) {
        double bid = super.bid(seller, startingPrice);
        if (wonAuction != null) {
            bid -= (wonAuction.getMarketPrice() - wonAuction.getPaidPrice()) + anullmentFee;
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
