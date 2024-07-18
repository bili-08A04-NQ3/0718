package top.niqiu.core.PhysicsObject.AnchoringPoint;

import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Values.Force;

public class AbsoluteAnchoringPoint extends AnchoringPoint {
    public Pos absolutePos;

    public AbsoluteAnchoringPoint(Scenario scenario, Pos absolutePos) {
        super(scenario);
        this.absolutePos = absolutePos;
    }

    @Override
    public Pos getAbsolutePos() {
        return this.absolutePos;
    }

    @Override
    public void applyForce(Force force) {

    }

    @Override
    public String toString() {
        return "AbsoluteAnchoringPoint{" +
                "absolutePos=" + absolutePos +
                '}';
    }
}
