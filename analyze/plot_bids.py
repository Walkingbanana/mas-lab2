import pandas as pd 
import matplotlib.pyplot as plt
import numpy as np

def get_agent_count(data): 
    agent_names = data.filter(like='Agent').columns
    return len(agent_names)


if __name__ == "__main__":
    target_file = "bids.csv"
    data = pd.read_csv(target_file, sep=",")
    data = data.fillna(method='ffill')
    
    arr = ['market_price', 'Agent_0']
    
    
    for seller_id, df in data.groupby(by='seller_id'):
        print(seller_id)
        avg_bidding_factor = df['market_price']/df['starting_price']
        avg_bidding_factor.plot(style='.-')
        df['Bidding_Factor_0'].plot(style='.-')
        df['Bidding_Factor_1'].plot(style='.-')
        df['Bidding_Factor_2'].plot(style='.-')
        df['Bidding_Factor_3'].plot(style='.-')
        plt.legend()
        plt.show()
        break
    
    data.reset_index().plot(kind='scatter', x='index' ,y='Bidding_Factor_0', color=data['Participated_0'].values)