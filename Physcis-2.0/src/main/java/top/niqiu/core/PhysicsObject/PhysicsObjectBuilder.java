package top.niqiu.core.PhysicsObject;

import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Setting.SettingField;
import top.niqiu.core.Text.TranslatedText;
import top.niqiu.core.Windows.Interface.ScenarioObjectBuilder;
import top.niqiu.core.Windows.Panels.JRangedSlider;
import top.niqiu.core.Windows.layout.VerticalFlowLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class PhysicsObjectBuilder extends ScenarioObjectBuilder {
    public Scenario scenario;

    public PhysicsObjectBuilder(Scenario scenario) {
        this.scenario = scenario;

    }

    /**
     * 展示于StatsPanel-displayPanel
     * @return displayPanel<-
     */
    public JPanel getDisplayPanel() {
        try {
            JPanel panel = new JPanel();
            panel.setLayout(new VerticalFlowLayout());
            JButton bottom = new JButton(new ImageIcon(getImage("")));
            // 让bottom适配父面板
            panel.add(bottom);
            JButton bottom_text = new JButton(new TranslatedText("top.niqiu.scenario.add_object.normal").getDisplayText());
            panel.add(bottom_text);

            Action action = new AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // 展示添加物品面板 -> 添加质点物品
                    new PhysicsObjectBuilderWindow(scenario);

                }
            };
            bottom_text.addActionListener(action);
            bottom.addActionListener(action);

            return panel;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Image getImage(String path) {
        InputStream inputStream = this.getClass().getResourceAsStream("/assets/interface_window/add_object.png");

        try {
            return ImageIO.read(inputStream).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            return null;
        }
    }
}

/**
 * 组件结构
 * contentPane
 *  - PreviewPanel
 *  - SettingPanel
 *     - [setValuePanel]
 */
class PhysicsObjectBuilderWindow extends JFrame implements ComponentListener {
    public Scenario scenario;
    public JPanel previewPanel;
    public JScrollPane previewScrollPane;
    public JPanel settingPanel;
    public JScrollPane settingScrollPane;
    public JSplitPane contentPane;
    public PhysicsObject object;

    public PhysicsObjectBuilderWindow(Scenario scenario) {
        this.scenario = scenario;

        object = new PhysicsObject(scenario);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addComponentListener(this);

        this.contentPane = new JSplitPane();

        this.previewPanel = new JPanel();
        this.settingPanel = new JPanel();

        this.loadSettingPanel();
        this.loadPreviewPanel();

        this.previewScrollPane = new JScrollPane(this.previewPanel);
        this.previewScrollPane.setLayout(new ScrollPaneLayout());
        this.previewScrollPane.setHorizontalScrollBarPolicy(previewScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.settingScrollPane = new JScrollPane(this.settingPanel);
        this.settingScrollPane.setLayout(new ScrollPaneLayout());
        this.settingScrollPane.setVerticalScrollBarPolicy(settingScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.contentPane.setRightComponent(this.previewScrollPane);
        this.contentPane.setLeftComponent(this.settingScrollPane);

        this.setContentPane(this.contentPane);

        this.repaint();
    }

    public void loadSettingPanel() {
        this.settingPanel.setLayout(new VerticalFlowLayout());
        this.settingPanel.add(new JLabel("New Object"));

        Field[] fields = object.getClass().getDeclaredFields();
        // 数据设置
        for (Field field : fields) {
            if (field.isAnnotationPresent(InitializedValue.class)) {
                InitializedValue settingField = field.getAnnotation(InitializedValue.class);
                field.setAccessible(true); // 允许访问私有字段

                String name = field.getName();

                JPanel setValuePanel = new JPanel();
                setValuePanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT));


                JRangedSlider slider = new JRangedSlider(name, settingField.min(), settingField.max(), settingField.defaultValue());
                slider.addChangeListener(e -> {
                    try {
                        field.set(object, slider.getValue());
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
    }

    public void loadPreviewPanel() {
        JButton button = new JButton("Create");
        button.addActionListener(e -> {
            if (this.check()) {
                this.build();
            }
        });
        this.previewPanel.add(button);
    }

    // 检查是否能创建
    public boolean check() {
        return true;
    }

    public void build() {
        this.scenario.addPhysicsObject(object);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
