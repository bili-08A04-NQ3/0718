package top.niqiu.core.PhysicsObject.AnchoringPoint;

import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Values.Force;
import top.niqiu.core.Windows.Interface.BackgroundPanel;

import java.awt.*;

public abstract class AnchoringPoint extends PhysicsObject {
    public AnchoringPoint(Scenario scenario) {
        super(scenario);
    }

    public abstract Pos getAbsolutePos();
    public double getAbsolutePosX(){
        return getAbsolutePos().getPosX();
    }

    public double getAbsolutePosY(){
        return getAbsolutePos().getPosY();
    }

    public abstract void applyForce(Force force);
    public void applyForce(double forceX, double forceY, String source, boolean display) {
        Force force = new Force(source, forceX, forceY);
        force.display = display;
        this.applyForce(force);
    }

    @Override
    public void finalTick() {
    }

    @Override
    public void drawObject(BackgroundPanel panel, Graphics g) {
    }
}
