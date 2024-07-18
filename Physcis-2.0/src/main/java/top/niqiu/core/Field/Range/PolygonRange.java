package top.niqiu.core.Field.Range;

import top.niqiu.core.Geometry.GeometryUtils;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolygonRange extends Range {
    public List<Pos> pos;

    public PolygonRange(Pos... pos) {
        this.pos = List.of(pos);
    }

    @Override
    public boolean include(double posX, double posY) {
        int overCount = 0;
        int belowCount = 0;
        Pos leastPos = pos.get(pos.size() - 1);

        for (Pos currentPos : pos) {
            if (GeometryUtils.isOverLine(leastPos, currentPos, new Pos(posX, posY))) {
                overCount++;
            }
            if (GeometryUtils.isBelowLine(leastPos, currentPos, new Pos(posX, posY))) {
                belowCount++;
            }

            leastPos = currentPos;
        }

        return overCount % 2 == 1 || belowCount % 2 == 1;
    }

    @Override
    public void drawEdge(Scenario scenario, Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3.0f));
        Pos leastPos = pos.get(pos.size() - 1);
        for (Pos currentPos : pos) {
            scenario.interfaceWindow.backgroundPanel.drawLine(g, leastPos, currentPos);
            leastPos = currentPos;
        }
        g2.setStroke(stroke);
    }
    @Override
    /**
     * 此方法返回的点坐标不精确
     */
    public List<Pos> getAllCrossingPoints(double k1, double b1) {
        List<Pos> list = new ArrayList<>();
        Pos leastPos = pos.get(pos.size() - 1);
        double k2;
        double b2;
        boolean flag;
        for (Pos currentPos : pos) {
            flag = false;
            double dx = currentPos.getPosX() - leastPos.getPosX();
            double dy = currentPos.getPosY() - leastPos.getPosY();
            k2 = dy / dx;
            if (dx == 0) {
                k2 = 10000000;
                flag = true;
            }
            b2 = currentPos.getPosY() - k2 * currentPos.getPosX();
            double crossingPointX = - (b2 - b1) / (k2 - k1);
            double crossingPointY = k1 * crossingPointX + b1;
            //System.out.println("crossingPointX: " + crossingPointX + " crossingPointY: " + crossingPointY);
            if ((crossingPointX >= Math.min(leastPos.posX, currentPos.posX) && crossingPointX <= Math.max(leastPos.posX, currentPos.posX))
            ||
            (flag && crossingPointY >= Math.min(leastPos.posY, currentPos.posY) && crossingPointY <= Math.max(leastPos.posY, currentPos.posY)))
            {
                //System.out.println(crossingPointY);
                list.add(new Pos(crossingPointX, crossingPointY));
                //System.out.println("crossingPointX: " + crossingPointX + " crossingPointY: " + crossingPointY);
            }
            leastPos = currentPos;
        }
        return list;
    }


    @Override
    public double getMinY() {
        double min = Double.MAX_VALUE;
        for (Pos currentPos : pos) {
            if (currentPos.getPosY() < min) {
                min = currentPos.getPosY();
            }
        }
        return min;
    }

    @Override
    public double getMaxY() {
        double max = Double.MIN_VALUE;
        for (Pos currentPos : pos) {
            if (currentPos.getPosY() > max) {
                max = currentPos.getPosY();
            }
        }
        return max;
    }

    @Override
    public double getMinX() {
        double min = Double.MAX_VALUE;
        for (Pos currentPos : pos) {
            if (currentPos.getPosX() < min) {
                min = currentPos.getPosX();
            }
        }
        return min;
    }

    @Override
    public double getMaxX() {
        double max = Double.MIN_VALUE;
        for (Pos currentPos : pos) {
            if (currentPos.getPosX() > max) {
                max = currentPos.getPosX();
            }
        }
        return max;
    }

    @Override
    public double getMinTanY(double k) {
        double min = Double.MAX_VALUE;
        for (Pos currentPos : pos) {
            double dy = currentPos.posY - currentPos.posX * k;
            if (dy < min) {
                min = dy;
            }
        }
        return min;
    }

    @Override
    public double getMaxTanY(double k) {
        double max = Double.MIN_VALUE;
        for (Pos currentPos : pos) {
            double dy = currentPos.posY - currentPos.posX * k;
            if (dy > max) {
                max = dy;
            }
        }
        return max;
    }
}
