import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewController {
    @FXML
    private TableView<Quote> table;

    @FXML
    private TableColumn hourCol;

    @FXML
    private TableColumn priceCol;

    public void initialize() {
        hourCol.prefWidthProperty().bind(table.widthProperty().divide(2));
        priceCol.prefWidthProperty().bind(table.widthProperty().divide(2));
        setTableItems();

        Main.quotesModel.quoteListProperty().addListener(new ListChangeListener<Quote>() {
            @Override
            public void onChanged(Change<? extends Quote> q) {
                setTableItems();
            }
        });
    }

    public void setTableItems() {
        table.getItems().removeAll(table.getItems());
        table.getItems().addAll(Main.quotesModel.quoteListProperty());
        hourCol.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(hourCol);
    }
}
