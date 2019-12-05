import org.apache.commons.cli.*;

public class AuctionArgumentParser
{
    private Options allOptions;
    private Option sellersOption;
    private Option buyersOption;
    private Option roundsOption;
    private Option startingPriceOption;
    private Option outputFilePath;

    private CommandLine cmd;

    public AuctionArgumentParser()
    {
        allOptions = new Options();

        sellersOption = new Option("K", "sellers", true, "The number of sellers");
        sellersOption.setRequired(true);
        allOptions.addOption(sellersOption);

        buyersOption = new Option("N", "buyers", true, "The number of buyers");
        buyersOption.setRequired(true);
        allOptions.addOption(buyersOption);

        roundsOption = new Option("R", "rounds", true, "The number of rounds");
        roundsOption.setRequired(true);
        allOptions.addOption(roundsOption);

        startingPriceOption = new Option("S", "startingPrice", true, "The universal maximum starting price");
        startingPriceOption.setRequired(true);
        allOptions.addOption(startingPriceOption);

        outputFilePath = new Option("outputFile", "outputFile", true, "The filepath in which the simulation will write the results.");
        outputFilePath.setOptionalArg(true);
        allOptions.addOption(outputFilePath);
    }

    public void parse(String[] args) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(allOptions, args);
    }

    public void printHelp()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("AuctionSimulator.exe", allOptions);
    }

    public int getNumberOfSellers()
    {
        return Integer.parseInt(cmd.getOptionValue(sellersOption.getOpt()));
    }

    public int getNumberOfBuyers()
    {
        return Integer.parseInt(cmd.getOptionValue(buyersOption.getOpt()));
    }

    public int getNumberOfRounds()
    {
        return Integer.parseInt(cmd.getOptionValue(roundsOption.getOpt()));
    }

    public int getUniversalStartingPrice()
    {
        return Integer.parseInt(cmd.getOptionValue(startingPriceOption.getOpt()));
    }

    public String getOutputFilePath()
    {
        return outputFilePath.getValue("./output.txt");
    }
}
