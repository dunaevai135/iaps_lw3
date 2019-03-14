
import org.primefaces.event.SlideEndEvent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

import javax.faces.annotation.FacesConfig;
import javax.validation.constraints.*;

import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

@FacesConfig(
        // Activates CDI build-in beans
        version = JSF_2_3
)

@Named("point")
@SessionScoped
public class PointService implements Serializable {
    @Min(-4) @Max(4)
    double x = 0;
    @Min(-5) @Max(5)
    double y = 0;
    @Min(2) @Max(5)
    double r = 4;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    String err; //TODO dell

    public LinkedList<Point> getPoints() {
        return points;
    }

    LinkedList<Point> points = new LinkedList<Point>();

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }


    public boolean inArea () {
        boolean isInArea;
        if ((x >= 0) && (y >= 0) && ((x * x + y * y) <= r*r)) {

            isInArea = true;
        } else {
            if ((x <= 0) && (y >= 0) && ( x >= -r/2 ) && ( y <= r )) {
                isInArea = true;
            } else {
                if ((x <= 0) && (y <= 0) && (y >= -1/2*x-r/2 )) {
                    isInArea = true;
                } else {
                    isInArea = false;
                }
            }
        }
        return isInArea;
    }

    public void addPoint() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
        Point point = new Point(getX(),getY(),getR(), inArea());
        points.add(point);
//        session.save(point);
//        session.getTransaction().commit();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {
            //
        }
    }

    public void onSlideSetX(SlideEndEvent event) {
        //TODO check limits
        setX(event.getValue());
    }

    public void onInputSetY(AjaxBehaviorEvent event) {
        //TODO check limits
//        setY((Double) event.getNewValue());
    }

    public void onSlideSetR(SlideEndEvent event) {
        //TODO check limits
        setR(event.getValue());
    }

}

