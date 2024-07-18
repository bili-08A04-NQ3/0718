package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;

public class UniformElectricField extends ElectricField {
    /**
     * 电场强度 E
     * @unit kg*m/(s^2*C)
     */
    public double intensityX = 0;
    public double intensityY = 0;
    public double intensity = 0;

    public UniformElectricField(Range range, double intensityX, double intensityY) {
        super(range);
        this.intensityX = intensityX;
        this.intensityY = intensityY;
        this.intensity = Math.sqrt(this.intensityX * this.intensityX + this.intensityY * this.intensityY);
    }

    @Override
    public double[] getIntensityAt(PhysicsObject object) {
        return new double[]{this.intensityX, this.intensityY};
    }

    @Override
    public void draw(Scenario scenario, Graphics g) {
        this.range.drawEdge(scenario, g);
        double interval =
                scenario.ElectricFieldIntervalMax
                        - (scenario.ElectricFieldIntervalMax - scenario.ElectricFieldIntervalMin)
                        * (this.intensity - scenario.ElectricFieldIntensityMin)
                        / (scenario.ElectricFieldIntensityMax - scenario.ElectricFieldIntensityMin);
        this.range.drawIsometricArrow(scenario, g, intensityX, intensityY, interval);
    }

    @Override
    public double getDisplayMaxIntensity() {
        return Math.sqrt(this.intensityX * this.intensityX + this.intensityY * this.intensityY);
    }

    @Override
    public double getDisplayMinIntensity() {
        return Math.sqrt(this.intensityX * this.intensityX + this.intensityY * this.intensityY);
    }
}
