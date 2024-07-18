package top.niqiu.core.Windows.Interface;

import top.niqiu.core.Field.Field;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Windows.GraphicsUtils;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    public InterfaceWindow interfaceWindow;
    /*------窗口中心相对于坐标原点的偏移------*/
    public int windowCentreXOffset;
    public int windowCentreYOffset;
    /*------单位长度------*/
    public double unitLength = 50;
    /*------启用坐标轴------*/
    public boolean enableAxis = true;

    public BackgroundPanel(InterfaceWindow interfaceWindow) {
        this.interfaceWindow = interfaceWindow;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(new Color(255, 249, 237));
        drawAxis(g);
        drawObjects(g);
        drawFields(g);
    }

    public void drawAxis(Graphics g) {
        if (this.enableAxis) {
            double dy = (double) interfaceWindow.getHeight() / this.unitLength;
            int index = (int) Math.log10(dy * 1.618) - 1;
            // x轴渲染
            if (this.windowCentreYOffset > interfaceWindow.getHeight()/ 2 || -this.windowCentreYOffset < interfaceWindow.getHeight() / 2) {
                g.drawLine(0, this.toWindowPosY(0), interfaceWindow.getWidth(), this.toWindowPosY(0));
                double dx = (double) interfaceWindow.getWidth() / this.unitLength;
                double k = Math.pow(10, index);
                double x1 = Math.floor(((double) -this.windowCentreXOffset / this.unitLength) / k) * k;
                for (double x = x1; x * this.unitLength < interfaceWindow.getWidth() - this.windowCentreXOffset; x += k) {
                    g.drawLine(this.toWindowPosX(x), this.toWindowPosY(0), this.toWindowPosX(x), this.toWindowPosY(0) - 6);
                    String num = String.valueOf(x);
                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    g.drawString(String.format("%.2f", x), this.toWindowPosX(x) - metrics.stringWidth(num) / 2, this.toWindowPosY(0) - 8);
                }
            }
            // y轴渲染
            if (this.windowCentreXOffset > interfaceWindow.getWidth()/ 2 || -this.windowCentreXOffset < interfaceWindow.getWidth() / 2) {
                g.drawLine(this.toWindowPosX(0), 0, this.toWindowPosX(0), interfaceWindow.getHeight());
                double k = Math.pow(10, index);
                double y1 = Math.floor(((double) this.windowCentreYOffset - interfaceWindow.getHeight()) / this.unitLength / k) * k;

                for (double y = y1; y * this.unitLength < interfaceWindow.getHeight() + this.windowCentreYOffset; y += k) {
                    g.drawLine(this.toWindowPosX(0), this.toWindowPosY(y), this.toWindowPosX(0) + 6, this.toWindowPosY(y));
                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    g.drawString(String.format("%.2f", y), this.toWindowPosX(0) + 10, this.toWindowPosY(y) + metrics.getHeight() / 6);
                }
            }
        }
    }

    public void drawObjects(Graphics g) {
        //g.drawString(Main.object.posX + "," + Main.object.posY, interfaceWindow.toWindowPosX(Main.object.posX), interfaceWindow.toWindowPosY(Main.object.posY));
        for (PhysicsObject object: interfaceWindow.scenario.physicsObjectList) {
            object.drawObject(this, g);
        }
    }

    public void drawFields(Graphics g) {
        for (Field field: interfaceWindow.scenario.fieldList) {
            field.draw(interfaceWindow.scenario, g);
        }
    }

    public void drawLine(Graphics g, Pos pos1, Pos pos2) {
        g.drawLine(this.toWindowPosX(pos1.posX), this.toWindowPosY(pos1.posY), this.toWindowPosX(pos2.posX), this.toWindowPosY(pos2.posY));
    }

    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine(this.toWindowPosX(x1), this.toWindowPosY(y1), this.toWindowPosX(x2), this.toWindowPosY(y2));
    }


    public void drawArrow(Graphics g, Pos pos1, Pos pos2) {
        GraphicsUtils.drawArrow(this.interfaceWindow.scenario, g, this.toWindowPosX(pos1.posX), this.toWindowPosY(pos1.posY), this.toWindowPosX(pos2.posX), this.toWindowPosY(pos2.posY));
    }

    public int toWindowPosX(double z) {
        return (int) (z * unitLength + this.windowCentreXOffset);
    }

    public int toWindowPosY(double z) {
        return (int) (-z * unitLength + this.windowCentreYOffset);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.requestFocusInWindow(); // 请求焦点
    }
}
