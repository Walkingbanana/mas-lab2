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

def bids_per_agent(data): 
    agent_bids = data[['Agent_0', 'Agent_1', 'Agent_2', 'Agent_3']].copy()
    
    for bids in agent_bids.iteritems():
        agent_name = bids[0]
        bid = list(bids[1])
        for index, value in enumerate(bid):
            if value == -1:
                bid[index] = bid[index-1]
            agent_bids[agent_name] = pd.Series(bid)
    
    return agent_bids


def plot_bids_per_agent(bid_results):
    plot_all(bid_results)
    
def bidding_factor_agents(data):
    bf = data[['Bidding_Factor_0', 'Bidding_Factor_1', 'Bidding_Factor_2', 'Bidding_Factor_3','market_price']]
    
    return bf

def plot_bidding_factors(bidding_factors):
    fig, ax1 = plt.subplots()
    
    color = 'tab:red'
    ax1.set_xlabel("Rounds x Auctions")
    ax1.set_ylabel("market price")
    ax1.plot(bidding_factors['market_price'], color = color)
    
    ax2 = ax1.twinx()
    
    color = 'tab:blue'
    ax2.set_ylabel("Bidding Factor")
    ax2.plot(bidding_factors['Bidding_Factor_0'], color = color)
    ax2.tick_params(axis='y', labelcolor = color)
    
    fig.tight_layout()
    plt.show()

if __name__ == "__main__":
    target_file = "bids.csv"
    data = pd.read_csv(target_file, sep=",")
    
    bid_results = bids_per_agent(data)
    plot_bids_per_agent(bid_results)
    
    bidding_factors = bidding_factor_agents(data)
    plot_bidding_factors(bidding_factors)
    