package top.niqiu.core.Field.Range;

import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 无边的
 */
public class UnlimitedRange extends Range {
    public UnlimitedRange() {
        super();
        super.centreX = 0;
        super.centreY = 0;
    }

    @Override
    public boolean include(double posX, double posY) {
        return true;
    }

    @Override
    public void drawEdge(Scenario scenario, Graphics g) {

    }

    @Override
    public List<Pos> getAllCrossingPoints(double k, double b) {
        return new ArrayList<>();
    }

    @Override
    public double getMinY() {
        return 0;
    }

    @Override
    public double getMaxY() {
        return 0;
    }

    @Override
    public double getMinX() {
        return 0;
    }

    @Override
    public double getMaxX() {
        return 0;
    }

    public double getMinTanY(double k) {
        return 0;
    }

    public double getMaxTanY(double k) {
        return 0;
    }
}
