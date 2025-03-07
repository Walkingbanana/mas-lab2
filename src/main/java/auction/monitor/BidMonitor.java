package auction.monitor;

import auction.AuctionResult;
import bidder.BidderAgent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BidMonitor implements AuctionMonitor {

    private BufferedWriter writer;
    private List<BidderAgent> agents;

    public BidMonitor(List<BidderAgent> agents, String outputFilePath) throws IOException {

        writer = new BufferedWriter(new FileWriter(outputFilePath));
        this.agents = agents;
    }

    public void onRoundAuctionStart() {
        try {
            StringBuilder builder = new StringBuilder();
            for (BidderAgent agent : agents) {
                builder.append(String.format("Agent_%d,", agent.getAgentID()));
                builder.append(String.format("Bidding_Factor_%d,", agent.getAgentID()));
                builder.append(String.format("Participated_%d,", agent.getAgentID()));
            }

            builder.append("seller_id,");
            builder.append("starting_price,");
            builder.append("market_price");
            writer.write(builder.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error while logging\n" + e.getMessage());
        }
    }

    public void onAuctionEnd(AuctionResult result) {
        try {
            StringBuilder builder = new StringBuilder();
            for (BidderAgent a : agents) {
                boolean b = result.getBids().containsKey(a);
                if (b) {
                    builder.append(result.getBids().get(a));
                }

                builder.append(",");
                builder.append(a.getBiddingFactors().get(result.getSeller()));
                builder.append(",");
                builder.append(b ? "1" : "0");
                builder.append(",");
            }
            builder.append(result.getSeller().getSellerID());
            builder.append(",");
            builder.append(result.getStartingPrice());
            builder.append(",");
            builder.append(result.getMarketPrice());
            writer.write(builder.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error while logging\n" + e.getMessage());
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
