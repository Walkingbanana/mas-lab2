package auction.monitor;

import auction.AuctionResult;

import java.io.Closeable;

public interface AuctionMonitor extends Closeable {
    public void onRoundAuctionStart();
    public void onAuctionEnd(AuctionResult result);
}
