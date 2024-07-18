package top.niqiu;

import com.formdev.flatlaf.FlatLightLaf;
import top.niqiu.core.Field.Range.PolygonRange;
import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.Field.UniformMagneticField;
import top.niqiu.core.PhysicsObject.AnchoringPoint.AbsoluteAnchoringPoint;
import top.niqiu.core.PhysicsObject.AnchoringPoint.ObjectAnchoringPoint;
import top.niqiu.core.PhysicsObject.Coils.Coil;
import top.niqiu.core.Windows.Data.DataWindow;
import top.niqiu.core.Windows.Interface.InterfaceWindow;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Pos.Pos;
import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Setting.SettingRegistry;
import top.niqiu.core.Text.TranslatedText;

import java.awt.*;
import java.util.List;

public class Main {
    /**
     * 1. ---窗口交互
     * 2. 物体碰撞
     * @param args
     */
    public static void main(String[] args) {
        TranslatedText.init();
        FlatLightLaf.install();
        Scenario scenario = Scenario.getDefaultScenario();
        SettingRegistry.registry(scenario);
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

        DataWindow dataWindow = new DataWindow("test", scenario, List.of(
                new DataWindow.DataSupplierPattern("Velocity", object0::getVelocity, Color.RED)
        ));

        scenario.interfaceWindow.setVisible(true);
        while (true) {
//            i++;wx
            scenario.tick();
//            window.windowCentreXOffset = (int) (- object.posX * window.unitLength) + window.windowWidth / 2;
//            window.windowCentreYOffset = (int) (object.posY * window.unitLength) + window.windowHeight / 2;
            //System.out.println(object.velocityX * object.velocityX + object.velocityY * object.velocityY);
        }
    }
}