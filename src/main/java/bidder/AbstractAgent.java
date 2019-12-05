package bidder;

public abstract class AbstractAgent implements BidderAgent {
    private static int nextAgentID = 0;

    private int agentID;

    public AbstractAgent() {
        this.agentID = nextAgentID;
        nextAgentID++;
    }

    public int getAgentID() {
        return agentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractAgent that = (AbstractAgent) o;
        return agentID == that.agentID;
    }

    @Override
    public int hashCode() {
        return getAgentID();
    }
}
