package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;

public class UniformMagneticField extends MagneticField {
    /**
     * 磁场强度B
     * @unit N/(A*m)
     */
    public double intensity = 0;

    /**
     * 磁场与水平面夹角(一般为pi/2或-pi/2)
     */
    public double direction = Math.PI / 2;

    public UniformMagneticField(Range range, double intensity, double direction) {
        super(range);
        this.intensity = intensity;
        this.direction = direction;
    }

    @Override
    public double getIntensityAt(PhysicsObject object) {
        return intensity * Math.sin(this.direction);
    }

    @Override
    public void draw(Scenario scenario, Graphics g) {
        this.range.drawEdge(scenario, g);
        double interval =
                scenario.MagneticIntervalMax
                        - (scenario.MagneticIntervalMax - scenario.MagneticIntervalMin)
                        * (this.intensity - scenario.MagneticIntensityMin)
                        / (scenario.MagneticIntensityMax - scenario.MagneticIntensityMin);
        if (this.direction > 0) {
            this.range.drawIsometricCross(scenario, g, interval);
        } else {
            this.range.drawIsometricPoint(scenario, g, interval);
        }
    }

    @Override
    public double getDisplayMaxIntensity() {
        return intensity;
    }

    @Override
    public double getDisplayMinIntensity() {
        return intensity;
    }
}
