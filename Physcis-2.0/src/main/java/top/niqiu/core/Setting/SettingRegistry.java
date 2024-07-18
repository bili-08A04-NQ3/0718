package top.niqiu.core.Setting;

import top.niqiu.core.Scenario.Scenario;
import top.niqiu.core.Text.Text;
import top.niqiu.core.Text.TranslatedText;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingRegistry<T> {
    public static List<SettingRegistry> settingRegistryList = new ArrayList<>();
    public Scenario scenario;
    public String registryName;
    public Text displayText;
    public Consumer<T> setter;
    public Supplier<T> supplier;

    public SettingRegistry(Scenario scenario, String registryName, Text displayText, Consumer<T> setter, Supplier<T> supplier) {
        this.scenario = scenario;
        this.registryName = registryName;
        this.displayText = displayText;
        this.setter = setter;
        this.supplier = supplier;
        settingRegistryList.add(this);
    }

    public void setValue(Object value) {
        this.setter.accept((T) value);
    }
    public static void registry(Scenario scenario) {
        new SettingRegistry<>(scenario, "UnitTime", new TranslatedText("top.niqiu.settings.unittime"), (unittime) -> scenario.unit_time = unittime, () -> scenario.unit_time);
        new SettingRegistry<>(scenario, "UnitLength", new TranslatedText("top.niqiu.settings.unitlength"), (unitlength) -> scenario.interfaceWindow.backgroundPanel.unitLength = unitlength, () -> scenario.interfaceWindow.backgroundPanel.unitLength);
        new SettingRegistry<>(scenario, "ElectricFieldIntervalMin", new TranslatedText("top.niqiu.settings.electricfieldintervalmin"), (electricfieldintervalmin) -> scenario.ElectricFieldIntervalMin = electricfieldintervalmin, () -> scenario.ElectricFieldIntervalMin);
        new SettingRegistry<>(scenario, "ElectricFieldIntervalMax", new TranslatedText("top.niqiu.settings.electricfieldintervalmax"), (electricfieldintervalmax) -> scenario.ElectricFieldIntervalMax = electricfieldintervalmax, () -> scenario.ElectricFieldIntervalMax);
        new SettingRegistry<>(scenario, "ElectricFieldIntensityMin", new TranslatedText("top.niqiu.settings.electricfieldintensitymin"), (electricfieldintensitymin) -> scenario.ElectricFieldIntensityMin = electricfieldintensitymin, () -> scenario.ElectricFieldIntensityMin);
        new SettingRegistry<>(scenario, "ElectricFieldIntensityMax", new TranslatedText("top.niqiu.settings.electricfieldintensitymax"), (electricfieldintensitymax) -> scenario.ElectricFieldIntensityMax = electricfieldintensitymax, () -> scenario.ElectricFieldIntensityMax);
        new SettingRegistry<>(scenario, "MagneticFieldIntervalMin", new TranslatedText("top.niqiu.settings.magneticfieldintervalmin"), (magneticfieldintervalmin) -> scenario.MagneticIntervalMin = magneticfieldintervalmin, () -> scenario.MagneticIntervalMin);
        new SettingRegistry<>(scenario, "MagneticFieldIntervalMax", new TranslatedText("top.niqiu.settings.magneticfieldintervalmax"), (magneticfieldintervalmax) -> scenario.MagneticIntervalMax = magneticfieldintervalmax, () -> scenario.MagneticIntervalMax);
        new SettingRegistry<>(scenario, "MagneticFieldIntensityMin", new TranslatedText("top.niqiu.settings.magneticfieldintensitymin"), (magneticfieldintensitymin) -> scenario.MagneticIntensityMin = magneticfieldintensitymin, () -> scenario.MagneticIntensityMin);
        new SettingRegistry<>(scenario, "MagneticFieldIntensityMax", new TranslatedText("top.niqiu.settings.magneticfieldintensitymax"), (magneticfieldintensitymax) -> scenario.MagneticIntensityMax = magneticfieldintensitymax, () -> scenario.MagneticIntensityMax);
        new SettingRegistry<>(scenario, "RecordForce", new TranslatedText("top.niqiu.settings.recordforce"), (recordforce) -> scenario.recordForce = recordforce, () -> scenario.recordForce);
        new SettingRegistry<>(scenario, "Pause", new TranslatedText("top.niqiu.settings.pause"), (pause) -> scenario.pause = pause, () -> scenario.pause);
        new SettingRegistry<>(scenario, "EnableAxis", new TranslatedText("top.niqiu.settings.enableaxis"), (enableaxis) -> scenario.interfaceWindow.backgroundPanel.enableAxis = enableaxis, () -> scenario.interfaceWindow.backgroundPanel.enableAxis);
    }
}
