import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    VBox vBox;

    private Parent root, root2;

    @FXML
    public void displayAbout() {
        Stage dialog = new Stage();

        dialog.initModality(Modality.APPLICATION_MODAL);

        Image logoImg = new Image(getClass().getClassLoader().getResourceAsStream("images/bluecoin.png"));

        Label app = new Label("AvantTrade");
        app.setFont(new Font(20));

        Font font = new Font(14);

        Label author = new Label("By: Edgar Cobos");
        author.setFont(font);

        Label source = new Label("Quotes from:");
        source.setFont(font);
        Hyperlink link = new Hyperlink("finviz.com/crypto.ashx");
        link.setFont(font);

        link.setOnAction(event -> Main.getServices().showDocument("https://" + link.getText()));

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(source, link);

        dialog.initOwner(Main.primaryStage);

        VBox vbox = new VBox();
        vbox.setMargin(author, new Insets(15, 0, 25, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new ImageView(logoImg), app, author, hbox);

        Scene dialogScene = new Scene(vbox, 400, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML
    public void addTableView() throws IOException {
        if (root == null) {
            root = FXMLLoader.load(getClass().getResource("TableView.fxml"));
            vBox.setMargin(root, new Insets(5, 0, 10, 0));
            vBox.getChildren().add(root);
        }
    }

    @FXML
    public void removeTableView() throws IOException {
        vBox.getChildren().remove(root);
        root = null;
    }

    @FXML
    public void addMyQuotes() {
        if (root2 == null) {
            root2 = new MyQuotes();
            vBox.setMargin(root2, new Insets(5,0,10,0));
            vBox.getChildren().add(root2);
        }
    }

    @FXML
    public void removeMyQuotes() {
        vBox.getChildren().remove(root2);
        root2 = null;
    }
}
