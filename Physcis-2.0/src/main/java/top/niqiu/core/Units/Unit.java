package top.niqiu.core.Units;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Unit {
    // 国际单位制中的基本单位
    public static Map<String, Unit> stringUnitMap = new HashMap<>();

    public String name;
    public String symbol;

    public Unit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        stringUnitMap.put(symbol, this);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(name, unit.name) && Objects.equals(symbol, unit.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, symbol);
    }
}
