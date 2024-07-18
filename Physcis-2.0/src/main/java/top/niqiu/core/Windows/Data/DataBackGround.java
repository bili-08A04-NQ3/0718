package top.niqiu.core.Windows.Data;

import top.niqiu.core.Pos.Pos;

import javax.swing.*;
import java.awt.*;

public class DataBackGround extends JPanel {
    public DataWindow dataWindow;

    /*---x轴的偏移---*/
    public double xOffset = 20;
    /*---y轴的最大最小值---*/
    public double yMax = Double.MIN_VALUE;
    public double yMin = Double.MAX_VALUE;

    public double xMax = Double.MIN_VALUE;
    public double xMin = Double.MAX_VALUE;

    public DataBackGround(DataWindow dataWindow) {
        this.dataWindow = dataWindow;
    }

    @Override
    public void paint(Graphics g) {
        this.setBounds(0, 0, (int) (dataWindow.getWidth() * 0.8), dataWindow.getHeight());
        drawAxis(g);
        drawLine(g);
    }

    public void drawAxis(Graphics g) {
        if (this.xOffset > 0 || this.xOffset < super.getWidth()) {
            g.drawLine((int) this.xOffset, 0, (int) this.xOffset, super.getHeight());
            double dy = this.yMax - this.yMin;
            int index = (int) Math.log10(dy * 1.618) - 1;
            double k = Math.pow(10, index);
            for (double y = this.yMax; y > this.yMin; y -= k) {
                g.drawLine((int) this.xOffset, (int) dataWindow.toWindowPosY(y), (int) this.xOffset + 6, (int) dataWindow.toWindowPosY(y));
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                g.drawString(String.format("%.2f", y), (int) this.xOffset + 10, (int) (dataWindow.toWindowPosY(y) + metrics.getHeight() / 6));
            }
            g.drawLine(0, (int) dataWindow.toWindowPosY(0), super.getWidth(), (int) dataWindow.toWindowPosY(0));
        }
    }

    public void drawLine(Graphics g) {
        int size = dataWindow.ySupplierPatterns.get(0).poses.size();

        //鼠标横线
        g.drawLine(dataWindow.leastMouseX, 0, dataWindow.leastMouseX, super.getHeight());
        //窗口竖线
        if (size > 2) {
            drawDataLines(g);
        }
    }

    public void drawDataLines(Graphics g) {
        var ref = new Object() {
            double currentXMax = Double.MIN_VALUE;
            double currentXMin = Double.MAX_VALUE;
            double currentYMax = Double.MIN_VALUE;
            double currentYMin = Double.MAX_VALUE;
        };
        dataWindow.ySupplierPatterns.forEach((pattern) -> {
            if (pattern.display) {

                //曲线
                g.setColor(pattern.color);
                Pos switchedPos = null;
                double minDistance = Double.MAX_VALUE;
                int pointCount = pattern.poses.size() - Math.max(0, pattern.poses.size() - dataWindow.displayTicks);
                int testInv = (pointCount < 4000) ? 1 : pointCount / 4000;
                for (int i = Math.max(0, pattern.poses.size() - dataWindow.displayTicks); i < pattern.poses.size() - 1; i++) {
                    FontMetrics metrics = g.getFontMetrics(g.getFont());
                    g.drawString(pattern.name, this.getWidth() - metrics.stringWidth(pattern.name) - 10, (int) dataWindow.toWindowPosY(pattern.poses.get(pattern.poses.size() - 2).posY));

                    Pos pos = pattern.poses.get(i);

                    ref.currentYMax = Math.max(pos.getPosY() + dataWindow.valueDisPlayIndex, ref.currentYMax);
                    ref.currentYMin = Math.min(pos.getPosY() - dataWindow.valueDisPlayIndex, ref.currentYMin);

                    ref.currentXMax = Math.max(pos.getPosX() + dataWindow.valueDisPlayIndex, ref.currentXMax);
                    ref.currentXMin = Math.min(pos.getPosX() - dataWindow.valueDisPlayIndex, ref.currentXMin);

                    double x = dataWindow.toWindowPosX(pos.getPosX());
                    double y = dataWindow.toWindowPosY(pos.getPosY());

                    double distance1 = Math.abs(- dataWindow.toWindowPosX(pos.getPosX()) + dataWindow.leastMouseX);
                    double distance2 = Math.abs(- dataWindow.toWindowPosY(pos.getPosY()) + dataWindow.leastMouseY);

                    if (i % testInv == 0) {
                        g.drawLine((int) x, (int) y, (int) x, (int) y);
                        switch (dataWindow.alignMethod) {
                            case 0: {
                                // 距离对齐
                                double distance_square = distance1 * distance1 + distance2 * distance2;
                                if (minDistance > distance_square) {
                                    switchedPos = pos;
                                    minDistance = distance_square;
                                }
                                break;
                            }
                            case 1: {
                                // x轴对齐
                                if (distance1 < minDistance) {
                                    switchedPos = pos;
                                    minDistance = distance1;
                                }
                                break;
                            }
                            case 2: {
                                // y轴对齐
                                if (distance2 < minDistance) {
                                    switchedPos = pos;
                                    minDistance = distance2;
                                }
                                break;
                            }
                        }
                    }
                }

                //文字
                if (switchedPos != null) {
                    int realX = (int) dataWindow.toWindowPosX(switchedPos.getPosX());
                    int realY = (int) dataWindow.toWindowPosY(switchedPos.getPosY());

//                    if (dataWindow.leastMouseX < this.getWidth()) {
//                        g.drawLine(realX, realY, dataWindow.leastMouseX, dataWindow.leastMouseY);
//                    }

                    // 画虚线
                    int unitLine = this.getWidth() / 50;
                    for (int k = 0; k < this.getWidth(); k += unitLine) {
                        if (k % 3 == 0) {
                            g.drawLine(k, realY, (unitLine + k), realY);
                        }
                    }
                    g.drawString(String.format("%.3f", switchedPos.posX) + "," + String.format("%.3f", switchedPos.posY), realX, realY);
                }
            }
        });
        this.xMax = ref.currentXMax;
        this.xMin = ref.currentXMin;

        this.yMax = ref.currentYMax;
        this.yMin = ref.currentYMin;
    }
}
