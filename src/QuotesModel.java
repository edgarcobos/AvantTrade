import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class QuotesModel {
    private SimpleListProperty<Quote> quoteListProperty;
    private SimpleDoubleProperty change;

    public QuotesModel() {
        ObservableList<Quote> observableQuoteList = (ObservableList<Quote>) FXCollections.observableArrayList(
                new Callback<Quote, Observable[]>() {
                    @Override
                    public Observable[] call(Quote q){
                        return new Observable[]{
                                q.hour,
                                q.price
                        };
                    }
                }
        );
        quoteListProperty = new SimpleListProperty<>(observableQuoteList);
        change = new SimpleDoubleProperty();
    }

    public ListProperty<Quote> quoteListProperty() {
        return quoteListProperty;
    }

    public Quote addQuote(Double hour, Double price) {
        Quote quote = new Quote(hour, price);
        quoteListProperty.add(quote);
        return quote;
    }

    public void setChange(Double changeValue) {
        change.set(changeValue);
    }

    public SimpleDoubleProperty changeProperty() {
        return change;
    }
}
