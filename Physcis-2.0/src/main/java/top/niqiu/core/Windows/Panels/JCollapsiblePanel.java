package top.niqiu.core.Windows.Panels;

import javax.swing.*;
import java.awt.*;

public class JCollapsiblePanel extends JPanel {

    private boolean isCollapsed;
    public JPanel contentPanel;
    public JPanel titlePanel;
    public JButton toggleButton;

    public Box box = Box.createVerticalBox();
    public JCollapsiblePanel(String title) {
        BorderLayout layout = new BorderLayout(0, 0);
        layout.setVgap(0);

        this.setLayout(layout);

        // 创建标题面板，包含一个 JButton 用于切换展开和收起状态
        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toggleButton = new JButton(title + (isCollapsed ? " [+] " : " [-] "));
        toggleButton.addActionListener(e -> {
            isCollapsed = !isCollapsed;
            toggleButton.setText(title + (isCollapsed ? " [+] " : " [-] "));
            contentPanel.setVisible(!isCollapsed);
        });
        titlePanel.add(toggleButton);

        // 创建内容面板，用于放置可展开/收起的组件
        contentPanel = new JPanel(new FlowLayout());
        contentPanel.setVisible(!isCollapsed); // 初始默认为展开状态

        // 将标题面板和内容面板添加到 CollapsiblePanel 中
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * 添加要展开/收起的组件到内容区域.
     *
     * @param component 要添加的组件
     * @return
     */
    public Component add(Component component) {
        contentPanel.add(component);
        return contentPanel;
    }

    private void LineFeed(int num) {
        String emptyString = "";
        for (int i = 0; i < num; i++) emptyString += " ";
        contentPanel.add(new JLabel(emptyString));
    }
}