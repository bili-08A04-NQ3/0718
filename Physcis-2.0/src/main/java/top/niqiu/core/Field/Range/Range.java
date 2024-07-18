package top.niqiu.core.Field.Range;

import top.niqiu.core.Windows.GraphicsUtils;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * 在绝对坐标下表达范围
 */
public abstract class Range {
    double centreX;
    double centreY;

    public abstract boolean include(double posX, double posY);

    public boolean include(Pos pos) {
        return include(pos.posX, pos.posY);
    }

    public abstract void drawEdge(Scenario scenario, Graphics g);
    public void drawIsometricArrow(Scenario scenario, Graphics g, double kx, double ky, double distance) {
        if (distance * scenario.interfaceWindow.backgroundPanel.unitLength < 3) return;
        if (kx == 0) kx = 0.0000000001;
        if (ky == 0) ky = 0.0000000001;
        double k = ky / kx;
        for (double b = this.getMinTanY(k); b <= this.getMaxTanY(k); b += distance / Math.cos(Math.atan(k))) {
            List<Pos> crossingPoints = this.getAllCrossingPoints(k, b);
            crossingPoints = GraphicsUtils.sortPosByX(crossingPoints);
            if (kx * ky < 0) {
                Collections.reverse(crossingPoints);
            }
            if (crossingPoints.size() > 0) {
                //System.out.println("---");
                //System.out.println(crossingPoints.size());
                Pos leastPos = crossingPoints.get(crossingPoints.size() - 1);
                for (int i = 0; i < crossingPoints.size(); i++) {
                    Pos pos = crossingPoints.get(i);
                    //System.out.println(pos.posX);
                    if (i % 2 == 1) {
                        if (pos.getPosX() > leastPos.getPosX() == (kx < 0 ^ ky > 0)) {
                            scenario.interfaceWindow.backgroundPanel.drawArrow(g, pos, leastPos);
                        } else {
                            scenario.interfaceWindow.backgroundPanel.drawArrow(g, leastPos, pos);
                        }
                    }
                    leastPos = pos;
                }
            }
        }
    }

    public void drawIsometricCross(Scenario scenario, Graphics g, double distance) {
        if (distance * scenario.interfaceWindow.backgroundPanel.unitLength < 3) return;
        double i = distance / 10;
        for (double x = this.getMinX() + i  ; x < this.getMaxX() - i ; x += distance) {
            for (double y = this.getMinY() + i ; y < this.getMaxY() - i ; y += distance) {
                if (this.include(x, y)) {
                    int u = (int) (i * scenario.interfaceWindow.backgroundPanel.unitLength);
                    g.drawLine(scenario.interfaceWindow.backgroundPanel.toWindowPosX(x) + u, scenario.interfaceWindow.backgroundPanel.toWindowPosY(y) + u, scenario.interfaceWindow.backgroundPanel.toWindowPosX(x) - u, scenario.interfaceWindow.backgroundPanel.toWindowPosY(y) - u);
                    g.drawLine(scenario.interfaceWindow.backgroundPanel.toWindowPosX(x) - u, scenario.interfaceWindow.backgroundPanel.toWindowPosY(y) + u, scenario.interfaceWindow.backgroundPanel.toWindowPosX(x) + u, scenario.interfaceWindow.backgroundPanel.toWindowPosY(y) - u);
                }
            }
        }
    }

    public void drawIsometricPoint(Scenario scenario, Graphics g, double distance) {
        if (distance * scenario.interfaceWindow.backgroundPanel.unitLength < 3) return;
        double i = distance / 10;
        for (double x = this.getMinX() + i  ; x < this.getMaxX() - i ; x += distance) {
            for (double y = this.getMinY() + i ; y < this.getMaxY() - i ; y += distance) {
                if (this.include(x, y)) {
                    g.drawLine(scenario.interfaceWindow.backgroundPanel.toWindowPosX(x), scenario.interfaceWindow.backgroundPanel.toWindowPosY(y), scenario.interfaceWindow.backgroundPanel.toWindowPosX(x), scenario.interfaceWindow.backgroundPanel.toWindowPosY(y));
                }
            }
        }
    }

    public abstract List<Pos> getAllCrossingPoints(double k, double b);

    public abstract double getMinY();
    public abstract double getMaxY();

    public abstract double getMinX();
    public abstract double getMaxX();

    public abstract double getMinTanY(double k);

    public abstract double getMaxTanY(double k);

}
