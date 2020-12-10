import javafx.beans.property.SimpleDoubleProperty;

public class Quote {
    protected SimpleDoubleProperty hour, price;

    public Quote(Double hour, Double price) {
        this.hour = new SimpleDoubleProperty(hour);
        this.price = new SimpleDoubleProperty(price);
    }

    public Double getHour() {
        return hour.get();
    }

    public double getPrice() {
        return price.get();
    }
}
