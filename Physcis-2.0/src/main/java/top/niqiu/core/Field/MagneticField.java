package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;

public abstract class MagneticField extends ForceField {
    public MagneticField(Range range) {
        super(range);
    }

    public abstract double getIntensityAt(PhysicsObject object);

    @Override
    public double[] getForce(PhysicsObject object) {
        double intensity = this.getIntensityAt(object);
        double forceX = - object.velocityY * object.charge * intensity;
        double forceY = object.velocityX * object.charge * intensity;
        return new double[]{forceX, forceY};
    }

//    @Override
//    public void drawObject(Scenario scenario, Graphics g, PhysicsObject object) {
//        //double[] forceV = this.getForce(object);
//        //double force = Math.sqrt(forceV[0] * forceV[0] + forceV[1] * forceV[1]);
//        if (this.range.include(object.posX, object.posY)) {
//            double radiosX = - object.mass * object.velocityY / object.charge / this.getIntensityAt(object);
//            double radiosY = object.mass * object.velocityX / object.charge / this.getIntensityAt(object);
//            scenario.interfaceWindow.drawLine(g, object.posX, object.posY, object.posX + radiosX, object.posY + radiosY);
//        }
//    }
}
