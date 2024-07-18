package top.niqiu.core.PhysicsObject.Coils;

import top.niqiu.core.PhysicsObject.AnchoringPoint.AnchoringPoint;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Windows.Interface.BackgroundPanel;

import java.awt.*;

/**
 * 满足胡克定律的弹簧
 */
public class Coil extends PhysicsObject {
    public AnchoringPoint point1;
    public AnchoringPoint point2;

    /**
     * 劲度系数
     *
     * @unit kg*m/s^3
     */
    public double stiffiness;

    /**
     * 原长
     *
     * @unit m
     */
    public double length;

    public final static int DISPLAY_LENGTH = 1;
    public final static int DISPLAY_STIFFINESS = 0;

    /**
     * 以P1, P2为端点, stiffiness为劲度系数, 原长为d(P1->P2)的弹簧
     *
     * @param point1 锚定点1
     * @param point2 锚定点2
     */
    public Coil(Scenario scenario, AnchoringPoint point1, AnchoringPoint point2, double stiffiness) {
        this(scenario, point1, point2, stiffiness, Math.sqrt(Math.pow(point1.getAbsolutePosX() - point2.getAbsolutePosX(), 2) + Math.pow(point1.getAbsolutePosY() - point2.getAbsolutePosY(), 2)));
    }


    /**
     * 以P1, P2为端点, stiffiness为劲度系数, 原长为length的弹簧
     *
     * @param point1 锚定点1
     * @param point2 锚定点2
     */
    public Coil(Scenario scenario, AnchoringPoint point1, AnchoringPoint point2, double stiffiness, double length) {
        super(scenario);
        this.point1 = point1;
        this.point2 = point2;
        this.stiffiness = stiffiness;
        this.length = length;
        this.scenario.coilStiffnessMax = Math.max(this.scenario.coilStiffnessMax, this.stiffiness + 1);
        this.scenario.coilStiffnessMin = Math.min(this.scenario.coilStiffnessMin, this.stiffiness - 1);
    }


    public void drawObject(BackgroundPanel panel, Graphics g) {
        Color color = g.getColor();

        Graphics2D g2 = (Graphics2D) g;  //g是Graphics对象

        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(this.displayColor);
        panel.interfaceWindow.backgroundPanel.drawLine(g, point1.getAbsolutePosX(), point1.getAbsolutePosY(), point2.getAbsolutePosX(), point2.getAbsolutePosY());

        g.setColor(color);
        g2.setStroke(stroke);
    }

    @Override
    public void coilTick() {
        double currentLength = Math.sqrt(Math.pow(point1.getAbsolutePosX() - point2.getAbsolutePosX(), 2) + Math.pow(point1.getAbsolutePosY() - point2.getAbsolutePosY(), 2));
        double force = stiffiness * (currentLength - length);
        double forceX = force * (point2.getAbsolutePosX() - point1.getAbsolutePosX()) / currentLength;
        double forceY = force * (point2.getAbsolutePosY() - point1.getAbsolutePosY()) / currentLength;
        point1.applyForce(forceX, forceY, "Coil", true);
        point2.applyForce(-forceX, -forceY, "Coil", true);
    }

    public double getCurrentLength() {
        return Math.sqrt(Math.pow(point1.getAbsolutePosX() - point2.getAbsolutePosX(), 2) + Math.pow(point1.getAbsolutePosY() - point2.getAbsolutePosY(), 2));
    }

    @Override
    public void finalTick() {
        this.updateColor();
    }

    public void updateColor() {
        double stretchRatio;
        double displacement = getCurrentLength() - length;
        switch (this.scenario.coilDisplayMethod) {
            case DISPLAY_LENGTH:
                stretchRatio = Math.min(1.0, Math.max(-1.0, displacement / length));
                break;
            default:
                stretchRatio = (this.stiffiness - this.scenario.coilStiffnessMin) / (this.scenario.coilStiffnessMax - this.scenario.coilStiffnessMin);
                return;
        }
        if (stretchRatio > 1) {
            stretchRatio = 1;
        }

        // 根据比例计算RGB值
        int red = (int) (255 * (1 + stretchRatio) / 2);
        int green = (int) (255 * (1 - Math.abs(stretchRatio)) / 2);
        int blue = (int) (255 * (1 - stretchRatio) / 2);
        Color c = new Color(red, green, blue);

        this.displayColor = c;
    }
}
