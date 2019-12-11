import pandas as pd 
import matplotlib.pyplot as plt
import numpy as np
# import tensorflow as keras

def plot_all(data):
    data.plot()
    plt.legend()
    plt.xlabel("Rounds x Auctions")
    plt.ylabel("Bidding Factor")
    plt.show()

def bids_per_seller(data): 
    agent_bids = data[['Agent_0', 'Agent_1', 'Agent_2', 'Agent_3']].copy()
    
    for bids in agent_bids.iteritems():
        agent_name = bids[0]
        bid = list(bids[1])
        for index, value in enumerate(bid):
            if value == -1:
                bid[index] = bid[index-1]
            agent_bids[agent_name] = pd.Series(bid)
            
    plot_all(agent_bids)
    
#def bidding_factor_agents(data):
        

if __name__ == "__main__":
    target_file = "bids.csv"
    data = pd.read_csv(target_file, sep=",")
    
    bids_per_seller(data)
    bidding_factor_agents(data)