from collections import defaultdict

import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import similaritymeasures
from sklearn.cluster import DBSCAN
import ast


def read(filename):
    df = pd.read_csv(filename)
    return df


def plot(df, name):
    line = sns.lineplot(data=df)
    plt.savefig(name + "_line.png")
    plt.clf()

    hist = sns.histplot(data=df)
    plt.savefig(name + "_hist.png")
    plt.clf()


if __name__ == '__main__':
    df_amount = read("tbl_zone_amount.csv")
    plot(df_amount, "amount")

    df_density = read("tbl_zone_density.csv")
    plot(df_density, "density")

    # df_way = read("tbl_way.csv")

    arr_ways = []
    with open('tbl_way.csv', 'r') as f:
        nums = f.read().splitlines()

    for i in range(1, len(nums)):
        lst = ast.literal_eval(nums[i])
        arr_ways.append(lst)

    n = len(arr_ways)
    dist_matrix = np.zeros((n, n))
    for i in range(n - 1):
        for j in range(i + 1, n):
            dist = similaritymeasures.frechet_dist(arr_ways[i], arr_ways[j])
            dist_matrix[i, j] = dist
            dist_matrix[j, i] = dist

    cl = DBSCAN(eps=2, min_samples=1, metric='precomputed')
    dbscan_clust = cl.fit(dist_matrix)
    clusters = cl.labels_

    dict_clust = defaultdict(set)
    for clust in clusters:
        dict_clust[int(clust)]=[]

    for route, clust in zip(arr_ways, clusters):
        dict_clust[int(clust)].append(route)

    nrows, ncols = 11, 11
    fig, ax = plt.subplots()
    dict_clust = dict(dict_clust)
    #
    # for i in range(nrows):
    #     for j in range(ncols):
    #         color = 'blue' if [i, j] in points else 'white'
    #         square = plt.Rectangle((j, nrows - 1 - i), 1, 1, facecolor=color, edgecolor='black')
    #         ax.add_patch(square)
    #
    # # Настройка отображения
    # ax.set_xlim(0, ncols)
    # ax.set_ylim(0, nrows)
    # ax.set_aspect('equal')
    # ax.axis('off')  # Отключаем оси
    #
    # plt.show()

    print(clusters)
