package top.niqiu.core.Windows.Interface;

import top.niqiu.core.PhysicsObject.PhysicsObjectBuilder;
import top.niqiu.core.Scenario.Scenario;

import javax.swing.*;
import java.awt.*;

public class AddObjectPanel extends JPanel {
    public AddObjectPanel(Scenario scenario) {
        this.setLayout(new FlowLayout());

        PhysicsObjectBuilder builder = new PhysicsObjectBuilder(scenario);

        this.add(builder.getDisplayPanel());
    }


}
