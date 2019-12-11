import pandas as pd 

if __name__ == "__main__":
    target_file = "bids.csv"
    data = pd.read_csv(target_file, sep=",")