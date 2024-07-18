package top.niqiu.core.PhysicsObject.AnchoringPoint;

import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Values.Force;

public class ObjectAnchoringPoint extends AnchoringPoint {
//    /**
//     * @param object 锚定物体
//     * @param absolutePos 锚定时的绝对坐标, 与物体构成相对坐标
//     */
//    public ObjectAnchoringPoint(PhysicsObject object, Pos absolutePos) {
//
//    }
    public PhysicsObject object;

    /**
     * 在物体重心锚定
     * @param object 锚定物体
     */
    public ObjectAnchoringPoint(PhysicsObject object) {
        super(object.scenario);
        this.object = object;
    }

    public Pos getAbsolutePos() {
        return object.getPos();
    }

    @Override
    public void applyForce(Force force) {
        object.applyForce(force);
    }

    @Override
    public String toString() {
        return "ObjectAnchoringPoint{" +
                "object=" + object +
                '}';
    }
}
