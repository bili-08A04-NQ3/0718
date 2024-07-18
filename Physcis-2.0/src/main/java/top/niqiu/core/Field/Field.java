package top.niqiu.core.Field;

import top.niqiu.core.Field.Range.Range;
import top.niqiu.core.PhysicsObject.PhysicsObject;
import top.niqiu.core.Scenario.Scenario;

import java.awt.*;

public abstract class Field {
    public static long usedId = 0;
    public long id;

    public String name= null;
    public Range range;

    public Field() {
        this.id = usedId;
        usedId++;
    }

    public Field(Range range) {
        this();
        this.range = range;
    }

    public abstract void analyzePhysicsObject(PhysicsObject object);

    public void draw(Scenario scenario, Graphics g) {
        this.range.drawEdge(scenario, g);
    }

    public void drawObject(Scenario scenario, Graphics g, PhysicsObject object) {

    }

    public double getDisplayMaxIntensity() {
        return 1;
    }

    public double getDisplayMinIntensity() {
        return 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
