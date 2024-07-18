package top.niqiu.core.Values;

public class Force {
    public String source;

    public double x;
    public double y;
    public boolean display = true;

    public Force(String source, double x, double y) {
        this.source = source;
        this.x = x;
        this.y = y;
    }

    public String getSource() {
        return source;
    }
}