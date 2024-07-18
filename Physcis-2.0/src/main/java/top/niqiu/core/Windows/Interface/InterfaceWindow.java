package top.niqiu.core.Windows.Interface;

import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 新建场景窗口
 * Parent: NewScenarioWindow
 * Children:
 */

/**
 * 组件结构
 * contentPane
 *  - backgroundPanel
 *  - statusPanel
 *     - objectPanel
 *     - fieldPanel
 *     - settingPanel
 */
public class InterfaceWindow extends JFrame implements MouseMotionListener, MouseWheelListener, KeyListener {
    public Scenario scenario;

    /*---负责所有物体和坐标轴的渲染---*/
    /*------------画面参数------------*/
    /*------鼠标坐标------*/
    public int leastMouseX = 0;
    public int leastMouseY = 0;

    public BackgroundPanel backgroundPanel;
    public JPanel statusPanel = new JPanel();
    public boolean displayStatus = true;


    public InterfaceWindow(Scenario scenario) {
        super("Main");
        this.scenario = scenario;

        scenario.setInterfaceWindow(this);
        this.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.setBackground(new Color(255, 243, 206));

        backgroundPanel = new BackgroundPanel(this);
        backgroundPanel.setBounds(0,0,this.getWidth(),this.getHeight());

        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);

        backgroundPanel.windowCentreXOffset = this.getWidth() / 2;
        backgroundPanel.windowCentreYOffset = this.getHeight() / 2;

        this.setContentPane(backgroundPanel);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(e -> {
            System.out.println("---");
            System.out.println("Key Event: " + e.getKeyCode());
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                System.out.println("Key Pressed: " + e.getKeyCode());
                // 在这里处理你的按键事件
                this.keyPressed(e);
                e.consume();
                return false;
            }
            return true;
        });
    }

    @Override
    public void repaint() {
        super.repaint();
        if (!displayStatus) {
            backgroundPanel.setBounds(0,0,this.getWidth(),this.getHeight());

        } else {
            backgroundPanel.setBounds(0,0,this.getWidth() , this.getHeight());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        backgroundPanel.windowCentreXOffset += (e.getX() - this.leastMouseX) / 2;
        backgroundPanel.windowCentreYOffset += (e.getY() - this.leastMouseY) / 2;
        this.leastMouseX = e.getX();
        this.leastMouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.leastMouseX = e.getX();
        this.leastMouseY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == 1 && backgroundPanel.unitLength > 1) {
            backgroundPanel.unitLength /= 1.15;
            backgroundPanel.windowCentreXOffset -= (int) ((backgroundPanel.windowCentreXOffset - e.getX()) * 0.15);
            backgroundPanel.windowCentreYOffset -= (int) ((backgroundPanel.windowCentreYOffset - e.getY()) * 0.15);
        }

        if (e.getWheelRotation() == -1 && backgroundPanel.unitLength < 3000) {
            backgroundPanel.unitLength *= 1.15;
            backgroundPanel.windowCentreXOffset += (int) ((backgroundPanel.windowCentreXOffset - e.getX()) * 0.15);
            backgroundPanel.windowCentreYOffset += (int) ((backgroundPanel.windowCentreYOffset - e.getY()) * 0.15);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() instanceof JButton) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar() + "_");
        if (e.getKeyChar() == ' ') {
            if (this.scenario.pause) {
                this.scenario.physicsObjectList.forEach((PhysicsObject object) -> object.forces.clear());
            } else {
            }
            this.scenario.pause = !this.scenario.pause;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() instanceof JButton) {
            e.consume();

        }
    }
}
