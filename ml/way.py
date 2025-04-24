import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import similaritymeasures
from sklearn.cluster import  DBSCAN
def read(filename):
    df = pd.read_csv(filename)
    return df

def plot(df,name):
    line = sns.lineplot(data=df)
    plt.savefig(name+"_line.png")
    plt.clf()

    hist = sns.histplot(data=df)
    plt.savefig(name+"_hist.png")
    plt.clf()


def frechet_distance(curve1, curve2):
    similaritymeasures.frechet_dist(curve1, curve2)


if __name__ == '__main__':
    df_amount = read("tbl_zone_amount.csv")
    plot(df_amount,"amount")

    df_density = read("tbl_zone_density.csv")
    plot(df_density,"density")

    df_way = read("tbl_way.csv")
    n = len(df_way)
    dist_matrix = np.zeros((n, n))
    for i in range(n):
        for j in range(i+1, n):
            dist = frechet_distance(df_way.loc[i, 'way'], df_way.loc[j, 'way'])
            dist_matrix[i, j] = dist
            dist_matrix[j, i] = dist

    cl = DBSCAN(eps=1000, min_samples=1, metric='precomputed')
    cl.fit(dist_matrix)
    print(cl.labels_)



