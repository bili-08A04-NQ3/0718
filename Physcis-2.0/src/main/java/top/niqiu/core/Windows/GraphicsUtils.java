package top.niqiu.core.Windows;

import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphicsUtils {
    private static final double arrowAngle = Math.PI / 8;
    private static final double arrowBasicLength = 0.05;
    public static void drawArrow(Scenario scenario, Graphics g, int hx, int hy, int ex, int ey) {
        //System.out.println("hx:" + hx + ";hy:" + hy + ";ex:" + ex + ";ey:" + ey);
        if (hx == ex && hy == ey) {
            return;
        }
        double arrowLength = arrowBasicLength * scenario.interfaceWindow.backgroundPanel.unitLength;

        g.drawLine(hx, hy, ex, ey);

        double dx = ex - hx;
        double dy = ey - hy;

        double a = (dx == 0) ? (dy < 0? Math.PI / 2: -Math.PI / 2) : Math.atan(dy / dx);

        double b, ox, oy;

        double u = (dx <= 0) ? a : a + Math.PI;

        b = u - arrowAngle;

        ox = Math.cos(b) * arrowLength + ex;
        oy = Math.sin(b) * arrowLength + ey;
        g.drawLine(ex, ey, (int) ox, (int) oy);

        b = u + arrowAngle;

        ox = Math.cos(b) * arrowLength + ex;
        oy = Math.sin(b) * arrowLength + ey;

        g.drawLine(ex, ey, (int) ox, (int) oy);
    }

    public static List<Pos> sortPosByX(List<Pos> posList) {
        Map<Double, Pos> xPosMap = new HashMap<>();
        for (Pos pos: posList) {
            xPosMap.put(pos.posX, pos);
        }
        return xPosMap.values().stream().sorted(Comparator.comparingDouble(Pos::getPosX)).collect(Collectors.toList());
    }
}
