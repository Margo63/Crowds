from collections import defaultdict, Counter

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

def clust(arr):
    n = len(arr)
    dist_matrix = np.zeros((n, n))
    for i in range(n - 1):
        for j in range(i + 1, n):
            dist = similaritymeasures.frechet_dist(arr[i], arr[j])
            dist_matrix[i, j] = dist
            dist_matrix[j, i] = dist
    #print(dist_matrix)
    cl = DBSCAN(eps=2, min_samples=1, metric='precomputed')
    dbscan_clust = cl.fit(dist_matrix)
    clusters = cl.labels_
    return clusters

if __name__ == '__main__':

    with open('tbl_report.txt', 'r') as f:
        report_lines = f.read().splitlines()

    board_rows =int(report_lines[0].split(" ")[-1])
    board_col = int(report_lines[1].split(" ")[-1])
    #print(board_rows, board_col)

    df_amount = read("tbl_zone_amount.csv")
    plot(df_amount, "amount")

    df_density = read("tbl_zone_density.csv")
    plot(df_density, "density")

    df_conflict = read("tbl_conflict_in_time.csv")
    df_amount_pedestrian = read("tbl_amount_pedestrian_in_time.csv")
    plot(df_conflict.join(df_amount_pedestrian),"amount_pedestrian_conflist")
    # df_way = read("tbl_way.csv")

    arr_ways = []
    with open('tbl_way.csv', 'r') as f:
        nums = f.read().splitlines()

    for i in range(1, len(nums)):
        lst = ast.literal_eval(nums[i])
        arr_ways.append(lst)




    df_conflict_point = read("tbl_conflict_point.csv")
    list_point = df_conflict_point[['row', 'column']].values.tolist()
    # conflict_points = [[point] for point in list_point]
    # clusters_conflict = clust(conflict_points)
    #
    # print(clusters_conflict)
    points = list(zip(df_conflict_point['column'], df_conflict_point['row']))
    freq = Counter(points)

    freq_df = pd.DataFrame(freq.items(), columns=['point', 'count'])
    freq_df[['column', 'row']] = pd.DataFrame(freq_df['point'].tolist(), index=freq_df.index)


    plot = sns.scatterplot(data=freq_df, x='column', y='row', size='count', sizes=(50, 300), legend=False, alpha=0.7)
    plt.xlim(0, board_rows)
    plt.ylim(0, board_col)

    plt.title('Частота встречаемости точек конфликта')
    plt.xlabel('column')
    plt.ylabel('row')
    plt.grid(True)
    plot.invert_yaxis()

    plt.savefig("conflict_point.png")
    plt.clf()



    # sns.scatterplot(df_conflict_point, x=df_conflict_point["column"],y=df_conflict_point['row'])
    # plt.show()

    clusters_ways = clust(arr_ways)
    print(clusters_ways)

    dict_clust = defaultdict(set)
    for clust in clusters_ways:
        dict_clust[int(clust)]=[]

    for route, clust in zip(arr_ways, clusters_ways):
        dict_clust[int(clust)].append(route)

    # nrows, ncols = 11, 11
    # fig, ax = plt.subplots()
    colors = ["red","green","blue","pink","magenta","black"]
    dict_clust = dict(dict_clust)


    for key,value in dict_clust.items():
        data = pd.DataFrame(value[0], columns=["row", "column"])
        #plot_way = sns.lineplot(data=data,x='column', y='row',marker='o')
        way_plot = sns.scatterplot(data=data,  x='column', y='row',  sizes=(50, 300), legend=False, alpha=0.7, color=colors[key], markers="o")
        plt.plot(data["column"],data["row"])
        # print(key,colors[key], value[0])
        # print(data)
        plt.xlim(0, board_rows)
        plt.ylim(0, board_col)
        plt.grid(True)
        way_plot.invert_yaxis()
        plt.savefig("way_"+str(key)+".png")
        plt.clf()



    # plot.invert_yaxis()
    # plt.show()
    # for i in range(nrows):
    #     for j in range(ncols):
    #         color = 'blue' if [i, j] in points else 'white'
    #         square = plt.Rectangle((j, nrows - 1 - i), 1, 1, facecolor=color, edgecolor='black')
    #         ax.add_patch(square)

    # # Настройка отображения
    # ax.set_xlim(0, ncols)
    # ax.set_ylim(0, nrows)
    # ax.set_aspect('equal')
    # ax.axis('off')  # Отключаем оси
    #
    # plt.show()
