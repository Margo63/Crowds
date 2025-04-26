package analyze;

import data.MapPoint;

import java.util.ArrayList;

public class FrechetDistance {
    // Вычисление евклидова расстояния между двумя точками
    private static double dist(MapPoint p1, MapPoint p2) {
        return Math.sqrt(Math.pow(p1.row()-p2.row(),2) + Math.pow(p1.column()- p2.column(), 2));
    }


    private static double[][] ca;

    public static double frechetDistance(ArrayList<MapPoint> curve1, ArrayList<MapPoint> curve2) {
        int n = curve1.size();
        int m = curve2.size();
        ca = new double[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                ca[i][j] = -1;

        return computation(curve1, curve2, n - 1, m - 1);
    }

    private static double computation(ArrayList<MapPoint> curve1, ArrayList<MapPoint> curve2, int i, int j) {

        if (ca[i][j] > -1) {
            return ca[i][j];
        } else if (i == 0 && j == 0) {
            ca[i][j] = dist(curve1.get(0), curve2.get(0));
        } else if (i > 0 && j == 0) {
            ca[i][j] = Math.max(computation(curve1, curve2, i - 1, 0), dist(curve1.get(i), curve2.get(0)));
        } else if (i == 0 && j > 0) {
            ca[i][j] = Math.max(computation(curve1, curve2, 0, j - 1), dist(curve1.get(0), curve2.get(j)));
        } else if (i > 0 && j > 0) {
            double minPrev = Math.min(
                    Math.min(computation(curve1, curve2, i - 1, j),
                            computation(curve1, curve2, i - 1, j - 1)),
                    computation(curve1, curve2, i, j - 1));

            ca[i][j] = Math.max(minPrev, dist(curve1.get(i), curve2.get(j)));
        } else {
            ca[i][j] = Double.POSITIVE_INFINITY;
        }
        return ca[i][j];
    }
}
