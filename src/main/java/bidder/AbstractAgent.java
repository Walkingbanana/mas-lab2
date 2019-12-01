package bidder;

public abstract class AbstractAgent implements BidderAgent {
    private static int nextAgentID = 0;

    private int agentID = getAgentID();

    public AbstractAgent() {
        this.agentID = nextAgentID;
        nextAgentID++;
    }

    public int getAgentID() {
        return agentID;
    }

    @Override
    public int hashCode() {
        return getAgentID();
    }
}
