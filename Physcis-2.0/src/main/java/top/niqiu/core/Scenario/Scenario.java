package top.niqiu.core.Scenario;

import top.niqiu.core.Field.ElectricField;
import top.niqiu.core.Field.Field;
import top.niqiu.core.Field.GravitationField;
import top.niqiu.core.Field.MagneticField;
import top.niqiu.core.PhysicsObject.Coils.Coil;
import top.niqiu.core.Setting.SettingField;
import top.niqiu.core.Windows.Data.DataWindow;
import top.niqiu.core.Windows.Interface.InterfaceWindow;
import top.niqiu.core.PhysicsObject.PhysicsObject;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    /*------------物理参数------------*/
    /**
     * 单位时间 10^-6 _ 10^-7 _ 10^-8
     * @unit s
     */
//    @SettingField
    public double unit_time = 0.0001;//0.00000001
    /**
     * 记录/渲染时间间隔 10^-5
     * @unit s
     */
    public double dataRecordInterval = 0.001;
    /**
     * 场景时间
     * @unit s
     */
    public double time = 0;
    /**
     * 重力加速度(地球环境适用)
     * @unit m/s^2
     */
    @SettingField
    public double gravitational_acceleration = 9.8;
    /**
     * 万有引力常数(太空环境适用)
     * @unit m^3/kg/s^2
     */
    @SettingField
    public double gravitational_constant = 6.67408 * Math.pow(10, -11);
//    /**
//     * 光速
//     * @unit m/s
//     */
//    @SettingField
//    public double lightSpeed = 299792458;
    /**
     * 库伦常数
     * @unit C/V
     */
    @SettingField
    public double coulomb_constant = 8.9875517873681764 * Math.pow(10, 9);

    public int recordCount = 0;

    /*------------系统参数------------*/
    public List<PhysicsObject> physicsObjectList = new ArrayList<>();
    public List<Field> fieldList = new ArrayList<>();
    public boolean recordForce = true;
    public boolean pause = true;
    public double ElectricFieldIntensityMin = Double.MAX_VALUE;
    public double ElectricFieldIntervalMin = 0.4;
    public double ElectricFieldIntensityMax = Double.MIN_VALUE;
    public double ElectricFieldIntervalMax = 0.8;
    public double MagneticIntensityMin = Double.MAX_VALUE;
    public double MagneticIntervalMin = 0.4;
    public double MagneticIntensityMax = Double.MIN_VALUE;
    public double MagneticIntervalMax = 0.8;
    public double coilStiffnessMax = Double.MIN_VALUE;
    public double coilStiffnessMin = Double.MAX_VALUE;
    public int coilDisplayMethod = Coil.DISPLAY_LENGTH;

    /*---------渲染------*/
    public InterfaceWindow interfaceWindow;
    public List<DataWindow> dataWindowList = new ArrayList<>();

    public Scenario() {
        InterfaceWindow window = new InterfaceWindow(this);
        this.setInterfaceWindow(window);

        new Thread(() -> {
            while (true) {
                this.tick();
            }
        }).start();
    }

    public static Scenario getDefaultScenario() {
        Scenario scenario = new Scenario();
        scenario.addField(new GravitationField(-10));
        return scenario;
    }

    public void tick() {
//        System.out.println("unitTime:"+ this.unitTime);
        if (!pause) {
            System.out.println(1);
            for (PhysicsObject object : physicsObjectList) {
                object.analyzeField();
                //-----测试
                object.coilTick();
                object.finalTick();
                //-----结束
            }
            this.time += this.unit_time;
            if (this.recordCount < (this.time / this.dataRecordInterval)) {
                this.recordCount++;
                interfaceWindow.repaint();
                dataWindowList.forEach(DataWindow::tick);
            }
        } else {
            dataWindowList.forEach(DataWindow::tick);
            interfaceWindow.repaint();
        }
    }
    public void addField(Field field) {
        fieldList.add(field);
    }

    public void addField(ElectricField field) {
        fieldList.add(field);
        this.ElectricFieldIntensityMax = Math.max(this.ElectricFieldIntensityMax, field.getDisplayMaxIntensity());
        this.ElectricFieldIntensityMin = Math.min(this.ElectricFieldIntensityMin, field.getDisplayMinIntensity()) + 0.0001;
    }

    public void addField(MagneticField field) {
        fieldList.add(field);
        this.MagneticIntensityMax = Math.max(this.MagneticIntensityMax, field.getDisplayMaxIntensity());
        this.MagneticIntensityMin = Math.min(this.MagneticIntensityMin, field.getDisplayMinIntensity()) + 0.0001;
    }

    public void addPhysicsObject(PhysicsObject object) {
        physicsObjectList.add(object);
    }

    public void setInterfaceWindow(InterfaceWindow interfaceWindow) {
        this.interfaceWindow = interfaceWindow;
    }
}
