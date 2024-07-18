package top.niqiu.core.Units;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UnitExpression {
    public static Map<Unit, Integer> DEFAULT_UNIT_MAP = new HashMap<>();
    // 分子/分母
    public Map<Unit, Integer> unitMap = new HashMap<>();

    public UnitExpression(Map<Unit, Integer> map) {
        this();
        this.unitMap = map;
    }

    public UnitExpression(List<Unit> numerator) {
        this();
        for (int i = 0; i < numerator.size(); i++) {
            int n = unitMap.get(numerator.get(i));
            this.unitMap.put(numerator.get(i), n + 1);
        }
    }
    public UnitExpression(List<Unit> numerator, List<Unit> denominator) {
        this();
        for (int i = 0; i < numerator.size(); i++) {
            int n = unitMap.get(numerator.get(i));
            this.unitMap.put(numerator.get(i), n + 1);
        }
        for (int i = 0; i < denominator.size(); i++) {
            int n = unitMap.get(denominator.get(i));
            this.unitMap.put(denominator.get(i), n - 1);
        }
    }

    public UnitExpression(Unit numerator, Unit denominator) {
        this(List.of(numerator), List.of(denominator));
    }

    public UnitExpression Multiply(UnitExpression expression) {
        Map<Unit, Integer> newMap = new HashMap<>();
        List<Unit> keys = DEFAULT_UNIT_MAP.keySet().stream().toList();
        for (int i = 0; i < keys.size(); i++) {
            Unit key = keys.get(i);
            int k = expression.unitMap.get(key) + this.unitMap.get(key);
            newMap.put(key, k);
        }
        return new UnitExpression(newMap);
    }

    public UnitExpression Divide(UnitExpression expression) {
        Map<Unit, Integer> newMap = new HashMap<>();
        List<Unit> keys = DEFAULT_UNIT_MAP.keySet().stream().toList();
        for (int i = 0; i < keys.size(); i++) {
            Unit key = keys.get(i);
            int k = - expression.unitMap.get(key) + this.unitMap.get(key);
            newMap.put(key, k);
        }
        return new UnitExpression(newMap);
    }

    private UnitExpression() {
        List<Unit> basicUnits = Unit.stringUnitMap.values().stream().toList();
        for (int i = 0; i < basicUnits.size(); i++) {
            unitMap.put(basicUnits.get(i), 0);
        }
    }

    @Override
    public String toString() {
        Map<Unit, Integer> numeratorUnitMap = new HashMap<>();
        Map<Unit, Integer> denominatorUnitMap = new HashMap<>();
        List<Unit> keys = DEFAULT_UNIT_MAP.keySet().stream().toList();
        for (int i = 0; i < keys.size(); i++) {
            Unit key = keys.get(i);
            if (unitMap.get(key) > 0) {
                numeratorUnitMap.put(key, unitMap.get(key));
            }else if(unitMap.get(key) < 0) {
                denominatorUnitMap.put(key, -unitMap.get(key));
            }
        }
        StringBuilder numeratorBuilder = new StringBuilder();
        StringBuilder denominatorBuilder = new StringBuilder();

        for (int i = 0; i < numeratorUnitMap.keySet().size(); i++) {
            Unit key = (Unit) numeratorUnitMap.keySet().toArray()[i];
            numeratorBuilder.append(key.symbol);
            int index = numeratorUnitMap.get(key);
            if (index > 1) {
                numeratorBuilder.append("^").append(index);
            }
            if (i != numeratorUnitMap.keySet().size() - 1) {
                numeratorBuilder.append("*");
            }
        }
        if (numeratorUnitMap.keySet().size() > 1) {
            numeratorBuilder.insert(0, "(").append(")");
        }
        for (int i = 0; i < denominatorUnitMap.keySet().size(); i++) {
            Unit key = (Unit) denominatorUnitMap.keySet().toArray()[i];
            denominatorBuilder.append(key.symbol);
            int index = denominatorUnitMap.get(key);
            if (index > 1) {
                denominatorBuilder.append("^").append(index);
            }
            if (i != denominatorUnitMap.keySet().size() - 1) {
                denominatorBuilder.append("*");
            }
        }
        if (denominatorUnitMap.keySet().size() > 1) {
            denominatorBuilder.insert(0, "(").append(")");
        }

        if (denominatorUnitMap.size() == 0) {
            return  numeratorBuilder.toString();
        }
        return numeratorBuilder + "/" + denominatorBuilder;
    }

    public static String UnitListToString(List<Unit> units) {
        return UnitListToString(units, false);
    }

    public static String UnitListToString(List<Unit> units, boolean forceBracket) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < units.size(); i++) {
            builder.append(units.get(i).symbol);
            if (i != units.size() - 1) {
                builder.append("*");
            }
        }
        if (units.size() > 1 || forceBracket) {
            builder.insert(0, "(");
            builder.append(")");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitExpression that = (UnitExpression) o;
        return Objects.equals(unitMap, that.unitMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitMap);
    }

    static {
        List<Unit> basicUnits = Unit.stringUnitMap.values().stream().toList();
        for (int i = 0; i < basicUnits.size(); i++) {
            DEFAULT_UNIT_MAP.put(basicUnits.get(i), 0);
        }
    }
}
