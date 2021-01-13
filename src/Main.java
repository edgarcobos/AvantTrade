import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    public static Window primaryStage;
    public static HostServices services;
    public static final QuotesModel quotesModel = new QuotesModel();

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;

            services = getHostServices();

            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

            primaryStage.setTitle("AvantTrade");
            primaryStage.setScene(new Scene(root, 450, 600));

            loadSplashScreen(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadSplashScreen(Stage primaryStage) {
        try {
            //create a new window for the splash screen, but hide all buttons/toolbar
            Stage splashStage = new Stage(StageStyle.UNDECORATED);

            //Load splash screen view FXML in a new window
            StackPane splashPane = FXMLLoader.load(getClass().getResource(("SplashScreen.fxml")));
            Scene splashScene = new Scene(splashPane, 300, 275);
            splashStage.setScene(splashScene);

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), splashPane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), splashPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            //After fade in, start fade out
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            //After fade out, close the splash window and open
            fadeOut.setOnFinished((e) -> {
                splashStage.close();
                primaryStage.show();
            });

            splashStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
        super.stop();
    }

    public static HostServices getServices() {
        return services;
    }
}
