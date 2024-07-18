package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;

public abstract class ForceField extends Field {
    public ForceField(Range range) {
        super(range);
    }

    /**
     * @param object 受力物体
     * @return 受力
     */
    public abstract double[] getForce(PhysicsObject object);

    @Override
    public void analyzePhysicsObject(PhysicsObject object) {
        if (super.range.include(object.posX, object.posY)) {
            double[] force = getForce(object);
            object.applyForce(force[0], force[1], (super.name == null ? "Field_" +super.id : super.name));
        }
    }
}
