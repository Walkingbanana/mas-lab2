package auction.monitor;

import auction.AuctionResult;
import auction.Seller;
import bidder.BidderAgent;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BiddingFactorMonitor implements Closeable, AuctionMonitor {
    private BufferedWriter writer;
    private List<BidderAgent> agents;

    public BiddingFactorMonitor(List<BidderAgent> agents, String outputFilePath) throws IOException {

        writer = new BufferedWriter(new FileWriter(outputFilePath));
        this.agents = agents;
    }

    public void onRoundAuctionStart() {
        try {
            StringBuilder builder = new StringBuilder();
            for (BidderAgent agent : agents) {
                for (Seller seller : agent.getBiddingFactors().keySet()) {
                    builder.append(String.format("Agent_%d_Seller_%d,", agent.getAgentID(), seller.getSellerID()));
                }
            }
            builder.deleteCharAt(builder.length() - 1); // Remove last coma
            writer.write(builder.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error while logging\n" + e.getMessage());
        }
    }

    public void onAuctionEnd(AuctionResult result) {
        try {
            StringBuilder builder = new StringBuilder();
            for (BidderAgent agent : agents) {
                for (Seller seller : agent.getBiddingFactors().keySet()) {
                    builder.append(String.format(Locale.ROOT, "%f,", agent.getBiddingFactors().get(seller)));
//                    builder.append(String.format(Locale.ROOT, "%f,",result.getBids().get(agent)));

                }
            }
            builder.deleteCharAt(builder.length() - 1); // Remove last comma
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
