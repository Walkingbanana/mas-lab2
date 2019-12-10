package auction;

import bidder.BidderAgent;

import java.io.*;
import java.util.*;

public class AuctionMonitor implements Closeable
{
    private BufferedWriter writer;
    private List<BidderAgent> agents;

    public AuctionMonitor(List<BidderAgent> agents, String outputFilePath) throws IOException {

        writer = new BufferedWriter(new FileWriter(outputFilePath));
        this.agents = agents;
    }

    public void onRoundAuctionStart()
    {
        try
        {
            StringBuilder builder = new StringBuilder();
            for(BidderAgent agent : agents)
            {
                for(Seller seller : agent.getBiddingFactors().keySet())
                {
                    builder.append(String.format("Agent_%d_Seller_%d,", agent.getAgentID(), seller.getSellerID()));
                }
            }
            builder.deleteCharAt(builder.length()-1); // Remove last comma
            writer.write(builder.toString());
            writer.newLine();
        }
        catch (IOException e)
        {
            System.out.println("Error while logging\n" + e.getMessage());
        }
    }

    public void onAuctionEnd(AuctionResult result){
        try
        {
            StringBuilder builder = new StringBuilder();
            for(BidderAgent agent : agents)
            {
                for(Seller seller : agent.getBiddingFactors().keySet())
                {
                    builder.append(String.format(Locale.ROOT, "%f,", agent.getBiddingFactors().get(seller)));
                }
            }
            builder.deleteCharAt(builder.length()-1); // Remove last comma
            writer.write(builder.toString());
            writer.newLine();
        }
        catch (IOException e)
        {
            System.out.println("Error while logging\n" + e.getMessage());
        }
    }

    public void close() throws IOException
    {
        writer.close();
    }
}
