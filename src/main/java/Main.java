import auction.*;
import bidder.BidderAgent;
import bidder.MASBidderAgent;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args)
    {
        AuctionArgumentParser parser = new AuctionArgumentParser();
        try
        {
            parser.parse(args);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
            parser.printHelp();
            System.exit(1);
        }

        List<Seller> sellers = new ArrayList(parser.getNumberOfSellers());
        for(int i = 0; i < parser.getNumberOfSellers(); i++)
        {
            sellers.add(new RandomSeller());
        }

        ArrayList<BidderAgent> buyers = new ArrayList<>(parser.getNumberOfBuyers());
        for(int i = 0; i < parser.getNumberOfBuyers(); i++)
        {
            buyers.add(new MASBidderAgent(0.8, 1.2));
        }

        AbstractAuction auction = new VickreyAuction(parser.getUniversalStartingPrice());
        auction.runAuctionRounds(parser.getNumberOfRounds(), sellers, buyers);
    }
}
