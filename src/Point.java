import javax.persistence.*;
import java.io.Serializable;

public class Point implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    int id;
    @Column(name = "X", nullable = false)
    double X;
    @Column(name = "Y", nullable = false)
    double Y;
    @Column(name = "R", nullable = false)
    double R;
    @Column(name = "InArea", nullable = false)
    boolean InArea;

    public Point() {}

    public Point(double x, double y, double r, boolean isInArea) {
        X = x;
        Y = y;
        R = r;
        this.InArea = isInArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public boolean isInArea() {
        return InArea;
    }

    public void setInArea(boolean inArea) {
        InArea = inArea;
    }
}

