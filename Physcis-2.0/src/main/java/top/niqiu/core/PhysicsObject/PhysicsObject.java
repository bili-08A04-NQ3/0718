package top.niqiu.core.PhysicsObject;

import top.niqiu.core.Field.Field;
import top.niqiu.core.PhysicsObject.AnchoringPoint.AnchoringPoint;
import top.niqiu.core.PhysicsObject.AnchoringPoint.ObjectAnchoringPoint;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Values.Force;
import top.niqiu.core.Windows.Interface.BackgroundPanel;
import top.niqiu.core.Windows.GraphicsUtils;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;

public class PhysicsObject {
    public Scenario scenario;

    /**
     * 质量 m
     * @unit kg
     */
    @InitializedValue
    public double mass = 1;

    /**
     * 电荷量 C
     * @unit A*s
     */
    @InitializedValue
    public double charge = 0;

    /**
     * 速度 v
     * @unit m/s
     */
    @InitializedValue
    public double velocityX = 0;
    @InitializedValue
    public double velocityY = 0;

    /**
     * 加速度 a
     * @unit m/s^2
     */
    public double accelerationX = 0;
    public double accelerationY = 0;

    /**
     * 角度 θ
     * @unit rad
     */
    @InitializedValue
    public double angle = 0;

    /**
     * 角速度 ω
     * @unit rad/s
     */
    @InitializedValue
    public double angularVelocity = 0;

    /**
     * 角加速度 a
     * @unit rad/s^2
     */
    public double angularAcceleration = 0;

    /**
     * 坐标 x,y
     * @unit m
     */
    @InitializedValue
    public double posX = 0;
    @InitializedValue
    public double posY = 0;

    /**
     * 在当前tick所受力
     * @unit N
     */
    private double forceX = 0;
    private double forceY = 0;

    public double leastForceX = 0;
    public double leastForceY = 0;


    /*------------系统参数------------*/
    public static int physicsObjectCount = 1;
    public static double forceLengthUnit = 0.1;
    public Map<String, Force> forces = new HashMap<>();
    public Color displayColor = Color.BLACK;

    // 物体id(0为地球)
    public long id;

    public PhysicsObject(Scenario scenario) {
        this.scenario = scenario;

        this.scenario.addPhysicsObject(this);
        this.id = physicsObjectCount;
        physicsObjectCount ++;
    }

    public void drawObject(BackgroundPanel panel, Graphics g) {
        //g.drawString(String.valueOf(panel.interfaceWindow.scenario.fieldList.get(1).range.include(posX, posY)), panel.interfaceWindow.toWindowPosX(this.posX) + 7, panel.interfaceWindow.toWindowPosY(this.posY));
        g.drawString("P" + String.valueOf(this.id), panel.interfaceWindow.backgroundPanel.toWindowPosX(this.posX), panel.interfaceWindow.backgroundPanel.toWindowPosY(this.posY));
        g.drawLine(panel.interfaceWindow.backgroundPanel.toWindowPosX(this.posX), panel.interfaceWindow.backgroundPanel.toWindowPosY(this.posY), panel.interfaceWindow.backgroundPanel.toWindowPosX(this.posX), panel.interfaceWindow.backgroundPanel.toWindowPosY(this.posY));

        if (scenario.recordForce) {
            Color color = g.getColor();
            g.setColor(Color.darkGray);
            Set<String> keySet = this.forces.keySet();
            for (String key: keySet) {
                Force force = this.forces.get(key);
                if (!(force.x == 0D && force.y == 0D) && force.display) {
                    this.drawForce(g, force);
                }
            }
            g.setColor(color);
            this.drawForce(g, new Force("F", this.leastForceX, this.leastForceY));
            if (!scenario.pause) {
                this.forces.clear();
            }
        }

        for (Field field: this.scenario.fieldList) {
            field.drawObject(this.scenario, g, this);
        }
    }

    public void drawForce(Graphics g, Force force) {
        if (force.display) {
            int startX = scenario.interfaceWindow.backgroundPanel.toWindowPosX(posX);
            int startY = scenario.interfaceWindow.backgroundPanel.toWindowPosY(posY);
            int endX = scenario.interfaceWindow.backgroundPanel.toWindowPosX(posX + force.x * forceLengthUnit);
            int endY = scenario.interfaceWindow.backgroundPanel.toWindowPosY(posY + force.y * forceLengthUnit);

            GraphicsUtils.drawArrow(scenario, g, startX, startY, endX, endY);
            g.drawString(force.source, endX, endY);
        }
    }

    public void coilTick() {

    }

    public void finalTick() {
        this.accelerationX = this.forceX / this.mass;
        this.accelerationY = this.forceY / this.mass;

        this.velocityX += this.accelerationX * this.scenario.unit_time;
        this.velocityY += this.accelerationY * this.scenario.unit_time;

        this.posX += this.scenario.unit_time * this.velocityX;
        this.posY += this.scenario.unit_time * this.velocityY;

        leastForceX = forceX;
        leastForceY = forceY;

        this.forceX = 0;
        this.forceY = 0;
    }

    public void applyForce(Pos absolutePos, Force force) {

    }

    public void applyForce() {
    }

    public void applyForce(Force force) {
        this.forceX += force.x;
        this.forceY += force.y;
        if (scenario.recordForce) {
            //System.out.println(source);
            this.forces.put(force.source, force);
        }
    }

    public void applyForce(double forceX, double forceY, String source) {
        //System.out.println(source);
        this.forceX += forceX;
        this.forceY += forceY;
        if (scenario.recordForce) {
            //System.out.println(source);
            this.forces.put(source, new Force(source, forceX, forceY));
        }
    }

    public void applyForce(double forceX, double forceY, String source, boolean display) {
        //System.out.println(source);
        this.forceX += forceX;
        this.forceY += forceY;
        if (scenario.recordForce) {
            //System.out.println(source);
            Force force = new Force(source, forceX, forceY);
            force.display = display;
            this.forces.put(source, force);
        }
    }

    public void analyzeField() {
        for (Field field: this.scenario.fieldList) {
            field.analyzePhysicsObject(this);
        }
    }

    public AnchoringPoint getAnchoringPoint() {
        return new ObjectAnchoringPoint(this);
    }
    public double getAccelerationX() {
        return accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public double getAngle() {
        return angle;
    }

    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public double getCharge() {
        return charge;
    }

    public double getForceX() {
        return forceX;
    }

    public double getForceY() {
        return forceY;
    }

    public double getMass() {
        return mass;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Pos getPos() {
        return new Pos(this.posX, this.posY);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getAcceleration() {
        return Math.sqrt(Math.pow(this.getAccelerationX(), 2) + Math.pow(this.getAccelerationY(), 2));
    }

    public double getVelocity() {
        return Math.sqrt(Math.pow(this.getVelocityX(), 2) + Math.pow(this.getVelocityY(), 2));
    }

    public double getLeastForce() {
        return Math.sqrt(Math.pow(this.getForceX(), 2) + Math.pow(this.getForceY(), 2));
    }

    public double getAngleVelocity() {
        return this.getAngularVelocity();
    }

    public double getAngleAcceleration() {
        return this.getAngularAcceleration();
    }

    public Color getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(Color displayColor) {
        this.displayColor = displayColor;
    }
}
