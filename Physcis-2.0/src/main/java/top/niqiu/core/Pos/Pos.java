package top.niqiu.core.Pos;

/**
 * 绝对坐标
 * 这个类仅用于需要大量坐标的地方
 * 对于少量坐标而言, posX与posY的单独量即可代替
 */
public class Pos {
    public double posX;
    public double posY;

    public Pos(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    

    @Override
    public String toString() {
        return "Pos{" +
                "posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
