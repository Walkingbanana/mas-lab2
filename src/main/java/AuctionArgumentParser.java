import org.apache.commons.cli.*;

public class AuctionArgumentParser
{
    private Options allOptions;
    private Option sellersOption;

    private Option increaseFactorsOption;
    private Option decreaseFactorsOption;

    private Option roundsOption;
    private Option startingPriceOption;
    private Option outputFilePath;
    private Option anullmentFee;

    private CommandLine cmd;
    private double[] increaseFactors;
    private double[] decreaseFactors;

    public AuctionArgumentParser()
    {
        allOptions = new Options();

        sellersOption = new Option("K", "sellers", true, "The number of sellers");
        sellersOption.setRequired(true);
        allOptions.addOption(sellersOption);

        roundsOption = new Option("R", "rounds", true, "The number of rounds");
        roundsOption.setRequired(true);
        allOptions.addOption(roundsOption);

        startingPriceOption = new Option("S", "startingPrice", true, "The universal maximum starting price");
        startingPriceOption.setRequired(true);
        allOptions.addOption(startingPriceOption);

        outputFilePath = new Option("of", "outputFile", true, "The filepath in which the simulation will write the results.");
        outputFilePath.setOptionalArg(true);
        allOptions.addOption(outputFilePath);

        increaseFactorsOption = new Option("if", "increaseFactors", true, "The increase factors for the buyers. The number of increase-factors have to be equal to the number of decrease-factors.");
        increaseFactorsOption.setOptionalArg(true);
        increaseFactorsOption.setArgs(Option.UNLIMITED_VALUES);
        allOptions.addOption(increaseFactorsOption);

        decreaseFactorsOption = new Option("df", "decreaseFactors", true, "The decrease factors for the buyers. The number of decrease-factors have to be equal to the number of increase-factors.");
        decreaseFactorsOption.setOptionalArg(true);
        decreaseFactorsOption.setArgs(Option.UNLIMITED_VALUES);
        allOptions.addOption(decreaseFactorsOption);

        anullmentFee = new Option("pf", "penaltyFactor", true, "The penalty factor for a bidder to retract from a purchase. If this is specified, leveled commitment auctions will be used.");
        anullmentFee.setOptionalArg(true);
        allOptions.addOption(anullmentFee);
    }

    public void parse(String[] args) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(allOptions, args);

        // Parse increase and decrease factors
        String[] strValuesDecrease = cmd.getOptionValue(decreaseFactorsOption.getOpt(), "0.99,0.99,0.99,0.99").split(",");
        String[] strValuesIncrease = cmd.getOptionValue(increaseFactorsOption.getOpt(), "1.01,1.01,1.01,1.01").split(",");
        if(strValuesDecrease.length != strValuesIncrease.length)
        {
            String errorMessage = String.format("The number of increase factors (%d) has to be the same as the number of decrease factors (%d).", strValuesIncrease.length, strValuesDecrease.length);
            throw new IllegalArgumentException(errorMessage);
        }

        decreaseFactors = new double[strValuesDecrease.length];
        increaseFactors = new double[strValuesIncrease.length];
        for(int i = 0; i < strValuesDecrease.length; i++)
        {
            decreaseFactors[i] = Double.parseDouble(strValuesDecrease[i]);
            increaseFactors[i] = Double.parseDouble(strValuesIncrease[i]);

            if(decreaseFactors[i] < 0 || decreaseFactors[i] > 1)
                throw new IllegalArgumentException("The decrease factors have to be between [0,1].");
            if(increaseFactors[i] <= 1)
                throw new IllegalArgumentException("The increase factors have to be greater than or equals to 1.");
        }
    }

    public void printHelp()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("AuctionSimulator.exe", allOptions);
    }

    public double[] getDecreaseFactors()
    {
        return decreaseFactors;
    }

    public double[] getIncreaseFactors()
    {
        return increaseFactors;
    }

    public int getNumberOfSellers()
    {
        return Integer.parseInt(cmd.getOptionValue(sellersOption.getOpt()));
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
        return cmd.getOptionValue(outputFilePath.getOpt(), "./output.txt");
    }

    public double getAnullmentFee(){
        String arg = cmd.getOptionValue(anullmentFee.getOpt());
        if(arg == null || arg.isEmpty()){
            return -1d;
        }
        double anullmentFee = Double.parseDouble(arg);
        if(anullmentFee < 0){
            throw new IllegalArgumentException("The penalty factor hast to be greater than zero.");
        }
        return anullmentFee;
    }
}
