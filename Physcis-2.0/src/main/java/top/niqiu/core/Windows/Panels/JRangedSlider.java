package top.niqiu.core.Windows.Panels;

import top.niqiu.core.Windows.layout.VerticalFlowLayout;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class JRangedSlider extends JPanel {
    public JPanel dataPanel;
    private JSlider slider;
    public double scale;

    JTextField minTextField, maxTextField, valueTextField;
    public JRangedSlider(String name, double initialMin, double initialMax, double initialValue, double scale) {
        this.scale = scale;
        if (name != null) {
            JLabel label = new JLabel(name);
            super.add(label);
        }

        slider = new JSlider((int) (initialMin * scale), (int) (initialMax * scale), (int) (initialValue * scale));

        this.dataPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        flowLayout.setHgap(10);
        this.dataPanel.setLayout(flowLayout);

        this.maxTextField = new JTextField(Double.toString(initialMax));
        this.minTextField = new JTextField(Double.toString(initialMin));
        this.valueTextField = new JTextField(Double.toString(initialValue));

        this.dataPanel.add(minTextField);
        this.dataPanel.add(valueTextField);
        this.dataPanel.add(maxTextField);

        super.setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT));
        super.add(dataPanel);
        super.add(slider);

        this.slider.addChangeListener(e -> {
            valueTextField.setText(Double.toString(slider.getValue() / scale));
        });

        this.maxTextField.addActionListener(
                e -> {
                    try {
                        int newMax = Integer.parseInt(maxTextField.getText());
                        this.setMaximum(newMax);
                    } catch (NumberFormatException ignored) {
                    }
                }
        );

        this.minTextField.addActionListener(
                e -> {
                    try {
                        int newMin = Integer.parseInt(minTextField.getText());
                        this.setMinimum(newMin);
                    } catch (NumberFormatException ignored) {
                    }
                }
        );

        this.valueTextField.addActionListener(
                e -> {
                    try {
                        int newValue = Integer.parseInt(valueTextField.getText());
                        this.setValue(newValue);
                    } catch (NumberFormatException ignored) {
                    }
                }
        );
    }

    public void repaint() {
        try {
        } catch (Exception ig) {

        }
    }

    public JRangedSlider(String name, double initialMin, double initialMax, double initialValue) {
        this(name, initialMin, initialMax, initialValue, 10);
    }

    public double getValue() {
        return (double) slider.getValue() / this.scale;
    }

    // 重写slider中的方法
    public void setValue(double value) {
        slider.setValue((int) (value * scale));
    }

    public void setMinimum(double min) {
        slider.setMinimum((int) (min * scale));
    }

    public void setMaximum(double max) {
        slider.setMaximum((int) (max * scale));
    }

    public void setMajorTickSpacing(double spacing) {
        slider.setMajorTickSpacing((int) (spacing * scale));
    }

    public void setMinorTickSpacing(double spacing) {
        slider.setMinorTickSpacing((int) (spacing * scale));
    }

    public void setPaintTicks(boolean paintTicks) {
        slider.setPaintTicks(paintTicks);
    }

    public void setPaintLabels(boolean paintLabels) {
        slider.setPaintLabels(paintLabels);
    }

    public void addChangeListener(ChangeListener listener) {
        slider.addChangeListener(listener);
    }
}
