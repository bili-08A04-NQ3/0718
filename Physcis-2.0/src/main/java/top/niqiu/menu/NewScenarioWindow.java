package top.niqiu.menu;

import com.formdev.flatlaf.FlatLightLaf;
import top.niqiu.core.Field.Range.PolygonRange;
import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.Field.UniformMagneticField;
import top.niqiu.core.PhysicsObject.AnchoringPoint.AbsoluteAnchoringPoint;
import top.niqiu.core.PhysicsObject.Coils.Coil;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.PhysicsObject.PhysicsObjectBuilder;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Setting.SettingField;
import top.niqiu.core.Text.TranslatedText;
import top.niqiu.core.Windows.Panels.JRangedSlider;
import top.niqiu.core.Windows.layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Field;

/**
 * 新建场景窗口
 * Parent: MenuWindow
 * Children:
 *  - InterfaceWindow
 */

/**
 * 组件结构
 * contentPane
 *  - PreviewPanel
 *  - SettingPanel
 *     - [setValuePanel]
 */
public class NewScenarioWindow extends JFrame implements ComponentListener {
    public JPanel previewPanel;
    public JPanel settingPanel;

    public Scenario scenario;
    public JSplitPane contentPane;

    public NewScenarioWindow() {
        super("New Scenario");

        scenario = Scenario.getDefaultScenario();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addComponentListener(this);

        contentPane = new JSplitPane();

        this.previewPanel = new JPanel();

        this.settingPanel = new JPanel();

        contentPane.setRightComponent(this.previewPanel);
        contentPane.setLeftComponent(this.settingPanel);
        contentPane.setDividerLocation(this.getWidth() / 2);

        this.settingPanel.setLayout(new VerticalFlowLayout());
        this.settingPanel.add(new JLabel("New Scenario"));

        this.setContentPane(contentPane);

        this.loadSettingPanel();
        this.loadPreviewPanel();

        this.repaint();
    }

    public void loadSettingPanel() {
        Field[] fields = scenario.getClass().getDeclaredFields();
        // 数据设置
        for (Field field : fields) {
            if (field.isAnnotationPresent(SettingField.class)) {
                SettingField settingField = field.getAnnotation(SettingField.class);
                field.setAccessible(true); // 允许访问私有字段

                String name = field.getName();

                JPanel setValuePanel = new JPanel();
                setValuePanel.setLayout(new VerticalFlowLayout());

                JRangedSlider slider = new JRangedSlider(name, settingField.min(), settingField.max(), settingField.defaultValue());
                slider.addChangeListener(e -> {
                    try {
                        field.set(scenario, slider.getValue());
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                StringBuilder builder = new StringBuilder();
                builder.append(new TranslatedText("top.niqiu.scenario.values.%s".formatted(name)).getDisplayText()).append("\n  - ");
                builder.append(new TranslatedText("top.niqiu.scenario.values.%s.description".formatted(name)).getDisplayText());
                JTextArea displayText = new JTextArea(builder.toString());
                displayText.setEditable(false);
                displayText.setLineWrap(true);

                setValuePanel.add(displayText);
                setValuePanel.add(slider);

                this.settingPanel.add(setValuePanel);
            }
        }

        // 场景类型设置
        // 下拉选框
        JComboBox<String> comboBox = new JComboBox();
        comboBox.addItem("");
    }

    public void loadPreviewPanel() {
        this.previewPanel.setLayout(new VerticalFlowLayout());

        JButton button = new JButton("Start!");
        button.addActionListener(e -> {
            this.scenario.interfaceWindow.setVisible(true);
            /**
             * test
             */
            double q = -1;

            //Range range1 = new PolygonRange(new Pos(-80, 0), new Pos(20, 0), new Pos(20, -40), new Pos(-80, -40));
            Range range1 = new PolygonRange(new Pos(-30, 0), new Pos(30, 0), new Pos(30, -30), new Pos(-30, -30));
            Range range2 = new PolygonRange(new Pos(-10, 15), new Pos(10, 15), new Pos(10, 20), new Pos(-10, 20));
            UniformMagneticField field1 = new UniformMagneticField(range1, 2.4, Math.PI / 2);
            UniformMagneticField field2 = new UniformMagneticField(range2, 1.75, -Math.PI / 2);
            scenario.addField(field1);
            scenario.addField(field2);


            PhysicsObject object0 = new PhysicsObject(scenario);
            object0.charge = q;
            object0.posX = 5;
            object0.posY = 5;

            PhysicsObject object1 = new PhysicsObject(scenario);
            object1.charge = q;
            object1.posX = -5;
            object1.posY = 5;

            PhysicsObject object2 = new PhysicsObject(scenario);
            object2.charge = q;
            object2.posX = -5;
            object2.posY = -5;

            PhysicsObject object3 = new PhysicsObject(scenario);
            object3.charge = q;
            object3.posX = 5;
            object3.posY = -5;

            Coil coil01 = new Coil(scenario, object0.getAnchoringPoint(), object1.getAnchoringPoint(), 100);
            Coil coil12 = new Coil(scenario, object1.getAnchoringPoint(), object2.getAnchoringPoint(), 100);
            Coil coil23 = new Coil(scenario, object2.getAnchoringPoint(), object3.getAnchoringPoint(), 100);
            Coil coil30 = new Coil(scenario, object3.getAnchoringPoint(), object0.getAnchoringPoint(), 100);

            AbsoluteAnchoringPoint anchor = new AbsoluteAnchoringPoint(scenario, new Pos(0, 0));
            Coil L0 = new Coil(scenario, anchor, object0.getAnchoringPoint(), 100);
            Coil L1 = new Coil(scenario, anchor, object1.getAnchoringPoint(), 100);

            // -------
            this.dispose();
        });
        this.previewPanel.add(button);
    }

    @Override
    public void repaint() {
        super.repaint();
        contentPane.setDividerLocation(this.getWidth() / 2);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        TranslatedText.init();
        NewScenarioWindow window = new NewScenarioWindow();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        this.repaint();
    }

    @Override
    public void componentShown(ComponentEvent e) {
        this.repaint();
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        this.repaint();
    }
}
