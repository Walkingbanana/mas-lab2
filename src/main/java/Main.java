import auction.*;
import auction.monitor.AuctionMonitor;
import auction.monitor.BidMonitor;
import auction.monitor.BiddingFactorMonitor;
import bidder.BidderAgent;
import bidder.LeveledBidderAgent;
import bidder.MASBidderAgent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.cli.*;

public class Main {
    // TODO FUCKING CHANGE THIS WHEN WE SUBMIT THIS CRAP - DO YOU HEAR ME KAI? - NO
    public static final boolean DEV_MODE = true;

    public static void main(String[] args) throws Exception {
        try {
            RunMainProgram(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) // Gotta catch em all... You son of a bitch I'm in!
        {
            System.out.println("An unexpected error occurred.");
            if (DEV_MODE) {
                throw e;
            }
        }
    }

    public static void RunMainProgram(String[] args) throws IOException {
        // Parse the arguments
        AuctionArgumentParser parser = new AuctionArgumentParser();
        try {
            parser.parse(args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            parser.printHelp();
            System.exit(1);
        }

        // Generate all sellers
        List<Seller> sellers = new ArrayList(parser.getNumberOfSellers());
        for (int i = 0; i < parser.getNumberOfSellers(); i++) {
            sellers.add(new RandomSeller());
        }

        // Generate all buyers
        double anullmentFee = parser.getAnullmentFee();
        double[] increaseFactors = parser.getIncreaseFactors();
        double[] decreaseFactors = parser.getDecreaseFactors();
        ArrayList<BidderAgent> buyers = new ArrayList<>(increaseFactors.length);
        for (int i = 0; i < increaseFactors.length; i++)
        {
            if (anullmentFee < 0) {
                buyers.add(new MASBidderAgent(increaseFactors[i], decreaseFactors[i]));
            } else {
                buyers.add(new LeveledBidderAgent(increaseFactors[i], decreaseFactors[i], anullmentFee));
            }
        }

        // Run the auction
        try (AuctionMonitor monitor = new BidMonitor(buyers, "./bids.csv")) {
            AbstractAuction auction = new VickreyAuction(parser.getUniversalStartingPrice(), monitor);
            AuctionScenarioResult results = auction.runAuctionRounds(parser.getNumberOfRounds(), sellers, buyers);

            // Log the results
            WriteResults(results, parser.getOutputFilePath());
        }
    }

    public static void WriteResults(AuctionScenarioResult results, String filePath) throws IOException {
        ScenarioStatistics stats = results.calculateStatistics();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write agents profits
            Map<BidderAgent, Double> buyerProfits = stats.getBuyerProfits();
            writer.write("--- Agents Profits ---");
            writer.newLine();
            for (BidderAgent agent : buyerProfits.keySet()) {
                writer.write(String.format("%d: %f", agent.getAgentID(), buyerProfits.get(agent)));
                writer.newLine();
            }

            // Write sellers profits
            Map<Seller, Double> sellerProfits = stats.getSellerProfits();
            writer.newLine();
            writer.write("--- Sellers Profits ---");
            writer.newLine();
            for (Seller seller : sellerProfits.keySet()) {
                writer.write(String.format("%d: %f", seller.getSellerID(), sellerProfits.get(seller)));
                writer.newLine();
            }

            // Write market price development
            Map<Seller, List<Double>> marketPrices = stats.getMarketPriceDevelopment();
            writer.newLine();
            writer.write("--- Market Price Development ---");
            writer.newLine();
            for (Seller seller : marketPrices.keySet()) {
                // Prime example of why Java is fucking stupid, what the fuck is this shit I just wanna concat a double list with a formatter
                String fuckingOutputFuckShit = marketPrices.get(seller).stream().map(d -> String.format(Locale.ROOT, "%.2f", d)).collect(Collectors.joining(","));
                writer.write(String.format("%d: %s", seller.getSellerID(), "[" + fuckingOutputFuckShit + "]"));
                writer.newLine();
            }
        }
    }
}
