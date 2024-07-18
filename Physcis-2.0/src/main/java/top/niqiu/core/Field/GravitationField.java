package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.UnlimitedRange;
import top.niqiu.core.PhysicsObject.PhysicsObject;

public class GravitationField extends ForceField {
    public double acceleration;

    /**
     * @param acceleration 重力加速度
     */
    public GravitationField(double acceleration) {
        super(new UnlimitedRange());
        this.acceleration = acceleration;
        super.name = "G";
    }

    @Override
    public double[] getForce(PhysicsObject object) {
        return new double[]{0, object.mass * this.acceleration};
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}
