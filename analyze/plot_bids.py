import pandas as pd 
import matplotlib.pyplot as plt
import matplotlib as mpl
import numpy as np

def get_agent_count(data): 
    agent_names = data.filter(like='Agent').columns
    return len(agent_names)

def plot_normalized_market_price(data):
    """
    Since the market price is calculated by 1/N (sum p * b_i)
    Where p is the starting_price, we can just plot the average bidding_factor
    That way we have a normalized market price, which can easily be compared with the bidding factors
    """
    avg_bidding_factor = data['market_price']/data['starting_price']
    avg_bidding_factor.plot(style="-", label="Avg. Marketprice")

def plot_bidding_factors(data, agent_id, show_participation=True):
    bidding_column = f'Bidding_Factor_{agent_id}'
    participation_column = f'Participated_{agent_id}'
    
    data[bidding_column].plot(style='-')
    if show_participation:
        plt.scatter(data.index, 
                    data[bidding_column],
                    c=data[participation_column],
                    cmap=mpl.colors.ListedColormap(['darkred', 'darkgreen']),
                    s=3,
                    zorder=10)


if __name__ == "__main__":
    target_file = "bids"
    data = pd.read_csv(f"{target_file}.csv", sep=",")
    data = data.fillna(method='ffill')
    
    arr = ['market_price', 'Agent_0']
    
    
    for seller_id, df in data.groupby(by='seller_id'):
        df = df.reset_index() # Reset index since we use that to specify the x-axis
        plt.figure(figsize=(12.8, 7.6))
        plot_normalized_market_price(df)
        
        for agent in range(get_agent_count(data)):
            plot_bidding_factors(df, agent)
            
        plt.legend()
        plt.title(f"Bidding-factor development for seller {seller_id}")
        plt.xlabel(f"Auction")
        plt.ylabel(f"Bidding-factor")
        plt.savefig(f"{target_file}_{seller_id}")