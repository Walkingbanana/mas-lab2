import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

def extract_num_agents_and_sellers(columns):
    '''
        Returns the number of agents and sellers, based on the column names
        We assume that the columns have the form "Agent_ID_Seller_ID"
        We also assume that there exists a agent with ID=0 and that the IDs have no gaps
    '''
    
    splits = [column.split('_') for column in columns]
    
    num_agents = max([int(t[1]) for t in splits])
    num_sellers = max([int(t[3]) for t in splits])
    
    return (num_agents + 1, num_sellers + 1)

def plot_all(data):
    data.plot()
    plt.legend()
    plt.xlabel("Rounds x Auctions")
    plt.ylabel("Bidding Factor")
    plt.show()
    
def plot_for_seller(data, seller_id, num_agents, show_market_price = True):
    columns = []
    for agent in range(num_agents):
        column = "Agent_{0}_Seller_{1}".format(agent, seller_id)
        columns.append(column)
    
    data = data[columns]
    if show_market_price:
        data = pd.concat([data[columns], data.mean(axis=1)], axis=1)
        data.columns = [*columns, "Marketprice"]
    plot_all(data)
    
def plot_for_seller(data, seller_id, num_agents, show_market_price = True):
    columns = []
    for agent in range(num_agents):
        column = "Agent_{0}_Seller_{1}".format(agent, seller_id)
        columns.append(column)
    
    data = data[columns]
    if show_market_price:
        data = pd.concat([data[columns], data.mean(axis=1)], axis=1)
        data.columns = [*columns, "Marketprice"]
    plot_all(data)
    
def plot_for_seller_and_agent(data, seller_id, agent_id, num_agents, show_market_price = True):
    columns = []
    for agent in range(num_agents):
        column = "Agent_{0}_Seller_{1}".format(agent, seller_id)
        columns.append(column)
    market_price = data.mean(axis=1)
    
    columns = ["Agent_{0}_Seller_{1}".format(agent_id, seller_id)]
    
    data = data[columns]
    if show_market_price:
        data = pd.concat([data[columns], market_price], axis=1)
        data.columns = [*columns, "Marketprice"]
    plot_all(data)
        

if __name__ == "__main__":
    target_file = "agents_development.csv"
    data = pd.read_csv(target_file, sep=",")
    
    num_agents, num_sellers = extract_num_agents_and_sellers(data.columns)
    plot_for_seller_and_agent(data, 0, 0, num_agents)
    plot_for_seller_and_agent(data, 0, 1, num_agents)