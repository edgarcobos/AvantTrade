import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MyQuotes extends BorderPane {
    private ListProperty<Quote> listProperty;
    private Double priceChange;
    private SimpleDoubleProperty change;

    public MyQuotes()
    {
        listProperty = Main.quotesModel.quoteListProperty();
        priceChange = Math.round((listProperty.get(listProperty.getSize()-1).getPrice() - listProperty.get(0).getPrice())*100.0)/100.0;
        change = Main.quotesModel.changeProperty();
        updateDisplay();

        Main.quotesModel.changeProperty().addListener((observable, oldValue, newValue) -> {
            priceChange = Math.round((listProperty.get(listProperty.getSize()-1).getPrice() - listProperty.get(0).getPrice())*100.0)/100.0;
            change = new SimpleDoubleProperty((Double) newValue);
            updateDisplay();
        });
    }

    private void updateDisplay() {
        Label percent = new Label(Math.round(change.get()*100.0)/100.0 + "%");
        percent.setFont(new Font(24));
        percent.setTextFill(Color.WHITE);

        Font font = new Font(16);

        Label price = new Label("Price");
        price.setFont(font);
        price.setTextFill(Color.WHITE);

        Label changeLabel = new Label("Change");
        changeLabel.setFont(font);
        changeLabel.setTextFill(Color.WHITE);

        HBox hBox = new HBox();
        hBox.setMargin(price, new Insets(0, 70, 0, 0));
        hBox.getChildren().addAll(price, changeLabel);

        font = new Font(18);

        Label priceValue = new Label("" + listProperty.get(listProperty.getSize()-1).getPrice());
        priceValue.setFont(font);
        priceValue.setTextFill(Color.WHITE);

        Label changeValue = new Label("" + priceChange);
        changeValue.setFont(font);
        changeValue.setTextFill(Color.WHITE);

        HBox hBox2 = new HBox();
        hBox2.setMargin(priceValue, new Insets(0, 25, 0, 0));
        hBox2.getChildren().addAll(priceValue, changeValue);

        Label title = new Label("Bitcoin");
        title.setFont(font);
        title.setTextFill(Color.WHITE);

        VBox vBox = new VBox();
        vBox.setMaxWidth(200);
        vBox.setBackground(new Background(new BackgroundFill(getColor(change.get()), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setMargin(percent, new Insets(5, 0, 0, 0));
        vBox.setMargin(hBox, new Insets(5, 0, 0, 0));
        vBox.setMargin(hBox2, new Insets(5, 0, 15, 0));
        vBox.getChildren().addAll(percent, hBox, hBox2, title);

        this.setCenter(vBox);
    }

    public Color getColor(Double change) {
        Color color = Color.DARKSLATEGRAY;
        if(change >= 3) {
            color = Color.DARKGREEN;
        }
        else if(change >= 2) {
            color = Color.FORESTGREEN;
        }
        else if(change >= 1) {
            color = Color.LIMEGREEN;
        }
        else if(change <= -1) {
            color = Color.MAROON;
        }
        else if(change <= -2) {
            color = Color.INDIANRED;
        }
        else if(change <= -3) {
            color = Color.TOMATO;
        }
        return color;
    }
}
