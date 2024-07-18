package top.niqiu.core.Units;

import java.util.List;

public class Units {
    public static Unit Length = new Unit("s", "m");
    public static Unit Mass = new Unit("m", "kg");
    public static Unit Time = new Unit("t", "s");
    public static Unit Current = new Unit("I", "A");
    public static Unit Temperature = new Unit("T", "K");
    public static Unit AmountOfSubstance = new Unit("M", "mol");
    public static Unit Radiance = new Unit("I", "cd");

    public static UnitExpression length = new UnitExpression(List.of(Length));
    public static UnitExpression mass = new UnitExpression(List.of(Mass));
    public static UnitExpression time = new UnitExpression(List.of(Time));
    public static UnitExpression current = new UnitExpression(List.of(Current));
    public static UnitExpression temperature = new UnitExpression(List.of(Temperature));
    public static UnitExpression amountOfSubstance = new UnitExpression(List.of(AmountOfSubstance));
    public static UnitExpression radiance = new UnitExpression(List.of(Radiance));

    public static UnitExpression empty = new UnitExpression(List.of());
    public static UnitExpression area = length.Multiply(length);//面积
    public static UnitExpression volume = area.Multiply(length);//体积
    public static UnitExpression density = mass.Divide(volume);//密度
    public static UnitExpression velocity = length.Divide(time);//速度
    public static UnitExpression acceleration = velocity.Divide(time);//加速度
    public static UnitExpression force = acceleration.Multiply(mass);//力
    public static UnitExpression energy = force.Multiply(length);//功
    public static UnitExpression power = energy.Divide(time);//功率
    public static UnitExpression pascal = force.Divide(area);//压强
    public static UnitExpression frequency = empty.Divide(time);//频率
    public static UnitExpression momentum = force.Multiply(time);//动量
    public static UnitExpression quantity_of_electric_charge = current.Multiply(time);//电荷量
    public static UnitExpression voltage = power.Divide(current);//电压
    public static UnitExpression resistance = voltage.Divide(current);//电阻
    public static UnitExpression capacitance = quantity_of_electric_charge.Divide(voltage);//电容量
    public static UnitExpression magnetic_flux_density = force.Divide(current).Divide(length);//磁感应强度
    public static UnitExpression magnetic_flux = magnetic_flux_density.Multiply(area);//磁通量
    public static UnitExpression inductance = resistance.Multiply(time);//电感
    public static void main(String[] args) {
        System.out.println("功的单位:" + pascal);
        System.out.println("动量的单位:" + momentum);
        System.out.println("电压的单位:" + voltage);
        System.out.println("电阻的单位:" + resistance);
    }
}
