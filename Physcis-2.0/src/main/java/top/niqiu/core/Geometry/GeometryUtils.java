package top.niqiu.core.Geometry;

import top.niqiu.core.Pos.Pos;

public class GeometryUtils {
    public static boolean isBelowLine(Pos p1, Pos p2, Pos Pc) {
        return isBelowLine(p1.posX, p1.posY, p2.posX, p2.posY, Pc.posX, Pc.posY);
    }

    public static boolean isBelowLine(double lineP1x, double lineP1y, double lineP2x, double lineP2y, double x, double y) {
        if ((x > lineP1x && x > lineP2x) || (x < lineP1x && x < lineP2x)) {
            return false;
        }

        double dx = lineP2x - lineP1x;
        if (dx == 0) return false;
        double dy = lineP2y - lineP1y;
        double k = dy / dx;

        // 相对坐标: 把待测点挪到原点, 比较b值
        lineP1x = lineP1x - x;

        lineP1y = lineP1y - y;

        x = 0;
        y = 0;

        return lineP1x * k - lineP1y <= 0;
    }

    public static boolean isOverLine(Pos p1, Pos p2, Pos pc) {
        return isOverLine(p1.posX, p1.posY, p2.posX, p2.posY, pc.posX, pc.posY);
    }

    public static boolean isOverLine(double lineP1x, double lineP1y, double lineP2x, double lineP2y, double x, double y) {
        if ((x > lineP1x && x > lineP2x) || (x < lineP1x && x < lineP2x)) {
            return false;
        }

        double dx = lineP2x - lineP1x;
        if (dx == 0) return false;
        double dy = lineP2y - lineP1y;
        double k = dy / dx;

        // 相对坐标: 把待测点挪到原点, 比较b值
        lineP1x = lineP1x - x;
        lineP2x = lineP2x - x;

        lineP1y = lineP1y - y;
        lineP2y = lineP2y - y;

        x = 0;
        y = 0;

        return lineP1x * k - lineP1y >= 0;
    }
}
