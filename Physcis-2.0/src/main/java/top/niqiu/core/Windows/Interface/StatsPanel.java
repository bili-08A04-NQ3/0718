package top.niqiu.core.Windows.Interface;

import top.niqiu.core.PhysicsObject.PhysicsObjectBuilder;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Text.TranslatedText;
import top.niqiu.core.Windows.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * 组件结构
 * contentPane
 *  - topStatus
 *    - topStatus_bottom
 *  - topBar
 *    - topBar_bottom_object
 *  - displayPanel
 *    - [displays...]{
 *        AddObjectPanel addObjectPanel
 *    }
 */
public class StatsPanel extends JPanel {
    // + -> 展开 | - -> 收起
    public JPanel topStatus;
    public JButton topStatus_bottom = new JButton("+");
    public JButton topStatus_pause = new JButton("Pause");

    public JPanel topBar;
    public JButton topBar_bottom_object = new JButton(new TranslatedText("top.niqiu.scenario.status.object").getDisplayText());

    public JPanel displayPanel;

    public StatsPanel(Scenario scenario) {
        this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT));
        this.setFocusable(false);
        this.topStatus = new JPanel();
        topStatus_bottom.addActionListener(e -> {
            scenario.interfaceWindow.displayStatus = !scenario.interfaceWindow.displayStatus;
            this.topStatus_bottom.setText(scenario.interfaceWindow.displayStatus ? "-" : "+");
        });
        this.topStatus.add(topStatus_bottom);
        topStatus_pause.addActionListener(e -> {
            scenario.pause = !scenario.pause;
            if (scenario.pause) {
                topStatus_pause.setText("Resume");
            } else {
                topStatus_pause.setText("Pause");
            }
        });
        this.topStatus.add(topStatus_pause);
        this.topStatus.setPreferredSize(new Dimension(30, 30));
        this.topStatus.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(topStatus);

        this.topBar = new JPanel();
        this.topBar.add(topBar_bottom_object);
        this.topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(topBar);

        this.displayPanel = new JPanel();
        this.displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.displayPanel.add(new AddObjectPanel(scenario));

        this.add(displayPanel);

    }


    @Override
    protected void processKeyEvent(KeyEvent e) {
        // 消耗键盘事件，阻止其在StatusPanel中产生作用
        e.consume();
    }


}
