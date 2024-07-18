package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;

public abstract class ElectricField extends ForceField {
    public ElectricField(Range range) {
        super(range);
    }

    public abstract double[] getIntensityAt(PhysicsObject object);

    @Override
    public double[] getForce(PhysicsObject object) {
        double[] intensity = this.getIntensityAt(object);
        return new double[]{intensity[0] * object.charge, intensity[1] * object.charge};
    }
}
