package top.niqiu.core.Windows.Data;

import top.niqiu.core.Windows.Panels.JRangedSlider;
import top.niqiu.core.Windows.layout.VerticalFlowLayout;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Text.TranslatedText;
import top.niqiu.core.Windows.Panels.JCollapsiblePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 组件结构
 * contentPane
 *  - functionBackGround
 *  - rightPanel_ScrollPane(rightPanel)
 *    - displayPanel
 *    - settingPanel
 */
public class DataWindow extends JFrame implements MouseMotionListener {
    public Scenario scenario;
    public DataBackGround functionBackGround;
    public JPanel rightPanel = new JPanel(new VerticalFlowLayout(FlowLayout.LEFT));
    public JScrollPane rightPanel_ScrollPane = new JScrollPane(rightPanel);
    public JCollapsiblePanel displayPanel;
    public JCollapsiblePanel settingPanel;
    /*------鼠标坐标------*/
    public int leastMouseX = 0;
    public int leastMouseY = 0;

    public Supplier<Double> xSupplier;
    public List<DataSupplierPattern> ySupplierPatterns;

    /*------------系统参数------------*/
    public int alignMethod = 0;
    public double valueDisPlayIndex = 1;
    public JLabel timeLabel;
    public int recordTicks = 50000;
    public int displayTicks = 5000;


    public DataWindow(String title, Scenario scenario, Supplier<Double> xSupplier, List<DataSupplierPattern> ySupplierPatterns) {
        super(title);
        this.scenario = scenario;
        this.xSupplier = xSupplier;
        this.ySupplierPatterns = ySupplierPatterns;
        scenario.dataWindowList.add(this);

        this.setBackground(new Color(252, 226, 145));
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setBackground(Color.WHITE);

        this.addMouseMotionListener(this);
        this.initPanel();
    }

    public DataWindow(String title, Scenario scenario, List<DataSupplierPattern> ySupplierPatterns) {
        this(title, scenario, null, ySupplierPatterns);
        this.xSupplier = () -> this.scenario.time;
    }

    public void initPanel() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);

        this.initDataPanel(contentPane);
        this.initRightPanel();
        /*
          DataPanel
          RightPanel
           - DisplayPanel
             - DataLines
             - DataShow
           - SettingPanel
         */

        this.getContentPane().add(contentPane);

        this.repaint();
    }

    public void initDataPanel(JPanel contentPane) {
        // 数据Panel
        this.functionBackGround = new DataBackGround(this);
        this.functionBackGround.setBounds(0, 0, (int) (this.getWidth() * 0.75), this.getHeight());
        contentPane.add(this.functionBackGround);
    }

    public void initRightPanel() {
        this.initDisplayPanel();
        this.initSettingPanel();
        this.getContentPane().add(this.rightPanel_ScrollPane);
    }
    public void initDisplayPanel() {
        this.displayPanel = new JCollapsiblePanel(new TranslatedText("top.niqiu.datawindow.display").getDisplayText());
        this.displayPanel.contentPanel.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 30, 0,true, true));


        this.timeLabel = new JLabel(new TranslatedText("top.niqiu.datawindow.display.time").getDisplayText().formatted(this.scenario.time));
        this.displayPanel.add(this.timeLabel);

        JCollapsiblePanel display_DataLine = new JCollapsiblePanel(new TranslatedText("top.niqiu.datawindow.display.dataline").getDisplayText());
        display_DataLine.contentPanel.setLayout(new VerticalFlowLayout(FlowLayout.LEFT));
        this.ySupplierPatterns.forEach((pattern) -> {
            JCollapsiblePanel showPanel = new JCollapsiblePanel(pattern.name);
            showPanel.toggleButton.setForeground(pattern.color);
            showPanel.contentPanel.setLayout(new VerticalFlowLayout(FlowLayout.LEFT)) ;
            //JCheckBox checkBox = new JCheckBox(pattern.name);

            double d = (!pattern.poses.isEmpty()) ? pattern.poses.get(pattern.poses.size() - 1).posY : 0;
            pattern.data = new JLabel(new TranslatedText("top.niqiu.datawindow.display.data").getDisplayText().formatted(d));
            showPanel.add(pattern.data);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(pattern.checkBox);
            JLabel label = new JLabel(new TranslatedText("top.niqiu.datawindow.display.line").getDisplayText());
            label.setBounds(0, 0, 100, 20);
            panel.add(label);
            panel.setSize(panel.getWidth(), 20);

            showPanel.add(panel);
            display_DataLine.add(showPanel);
        });

        this.displayPanel.add(display_DataLine);
//        settingPanel.add(showPanel);
        this.rightPanel.add(displayPanel);
    }

    public void initSettingPanel() {
        this.settingPanel = new JCollapsiblePanel(new TranslatedText("top.niqiu.datawindow.setting").getDisplayText());
        this.settingPanel.contentPanel.setLayout(new VerticalFlowLayout());

        this.settingPanel.add(new JLabel(new TranslatedText("top.niqiu.datawindow.setting.displaylength").getDisplayText()));
        JSlider displayTicksSlider = new JSlider(4000, 12000, this.displayTicks);
        displayTicksSlider.setMajorTickSpacing(4000);
        displayTicksSlider.setMinorTickSpacing(1000);
        displayTicksSlider.setPaintLabels(true);
        displayTicksSlider.setPaintTicks(true);
        displayTicksSlider.setBounds(0, 0, (int) (this.settingPanel.getWidth() * 0.1), 100);
        displayTicksSlider.addChangeListener(e -> DataWindow.this.displayTicks = displayTicksSlider.getValue());
        this.settingPanel.add(displayTicksSlider);


        this.settingPanel.add(new JLabel(new TranslatedText("top.niqiu.datawindow.setting.tps").getDisplayText()));
        JRangedSlider tpsSlider = new JRangedSlider("TPS", 6.0D, 8.0D, 7.0D);
        tpsSlider.setMajorTickSpacing(1);
        tpsSlider.setMinorTickSpacing(1);
        tpsSlider.setPaintLabels(true);
        tpsSlider.setPaintTicks(true);
        tpsSlider.setBounds(0, 0, (int) (this.settingPanel.getWidth() * 0.1), 100);
        tpsSlider.addChangeListener(e -> {
            scenario.unit_time = (double) 1 / Math.pow(10, tpsSlider.getValue());
            System.out.println(tpsSlider.getValue());
            System.out.println(scenario.unit_time + "_");
        });
        this.settingPanel.add(tpsSlider);

        this.rightPanel.add(this.settingPanel);
    }


    @Override
    public void repaint() {
        super.repaint();
        this.functionBackGround.setBounds(0, 0, (int) (this.getWidth() * 0.75), this.getHeight());
        this.rightPanel_ScrollPane.setBounds((int) (this.getWidth() * 0.75), 0, (int) (this.getWidth() * 0.25), this.getHeight());

        this.timeLabel.setText(new TranslatedText("top.niqiu.datawindow.display.time").getDisplayText().formatted(this.scenario.time));
        this.ySupplierPatterns.forEach((pattern) -> {
            double d = (!pattern.poses.isEmpty()) ? pattern.poses.get(pattern.poses.size() - 1).posY : 0;
            pattern.data.setText(new TranslatedText("top.niqiu.datawindow.display.data").getDisplayText().formatted(d));
        });
    }

    public void tick() {
        if (!scenario.pause) {
            double x = xSupplier.get();
            ySupplierPatterns.forEach((pattern) -> {
                double y = pattern.supplier.get();
                pattern.poses.add(new Pos(x, y));
                if (pattern.display) {
                    functionBackGround.yMax = Math.max(y + valueDisPlayIndex, functionBackGround.yMax);
                    functionBackGround.yMin = Math.min(y - valueDisPlayIndex, functionBackGround.yMin);
                }
            });
            functionBackGround.xMax = Math.max(x + valueDisPlayIndex, functionBackGround.xMax);
            functionBackGround.xMin = Math.min(x - valueDisPlayIndex, functionBackGround.xMin);

            long d = ySupplierPatterns.get(0).poses.size() - this.recordTicks;
            for (long i = 0; i < d; i++){
                ySupplierPatterns.forEach((pattern) -> pattern.poses.remove(0));
            }
          }
        this.repaint();
    }

    public double toWindowPosX(double z) {
        return (z - valueDisPlayIndex - functionBackGround.xMin) / (functionBackGround.xMax - functionBackGround.xMin - valueDisPlayIndex * 2) * (functionBackGround.getWidth() - functionBackGround.xOffset) + functionBackGround.xOffset;
    }

    public double toWindowPosY(double z) {
        return 0.95 * (functionBackGround.getHeight() - (z - valueDisPlayIndex - functionBackGround.yMin) / (functionBackGround.yMax - functionBackGround.yMin - valueDisPlayIndex * 2) * functionBackGround.getHeight()) ;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.leastMouseX = e.getX();
        this.leastMouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.leastMouseX = e.getX();
        this.leastMouseY = e.getY();
    }

    public static class DataSupplierPattern {
        public String name;
        public Supplier<Double> supplier;
        Color color;
        public List<Pos> poses = new ArrayList<>();
        public JCheckBox checkBox;
        public JLabel data;
        public boolean display = true;

        public DataSupplierPattern(String name, Supplier<Double> supplier, Color color) {
            this.name = name;
            this.supplier = supplier;
            this.color = color;

            this.checkBox = new JCheckBox(name);
            this.checkBox.setSelected(true);
            this.checkBox.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    display = checkBox.isSelected();
                }
            });
        }
    }
}