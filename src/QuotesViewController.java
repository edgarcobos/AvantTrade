import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.json.*;

public class QuotesViewController {
    @FXML
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;

    @FXML
    LineChart<Number,Number> lineChart;

    public void initialize() {
        lineChart.getData().clear();

        XYChart.Series series = new XYChart.Series();

        String json = "";
        try {
            String command = "curl -X GET https://finviz.com/crypto.ashx";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));

            Process process = processBuilder.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                if(inputLine.contains("tiles")) {
                    int begin = inputLine.indexOf("{");
                    int end = inputLine.lastIndexOf("}") + 1;
                    json = inputLine.substring(begin, end);
                    break;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONObject("BTCUSD").getJSONArray("sparkline");

        Double min = arr.getDouble(0);
        Double max = arr.getDouble(0);
        Main.quotesModel.quoteListProperty().clear();
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getDouble(i) < min) {
                min = arr.getDouble(i);
            }
            else if (arr.getDouble(i) > max) {
                max = arr.getDouble(i);
            }
            Main.quotesModel.addQuote(Double.valueOf(i+1)/12.5, arr.getDouble(i));
            series.getData().add(new XYChart.Data<Number, Number>(Double.valueOf(i+1)/12.5, arr.getDouble(i)));
        }
        ListProperty<Quote> listProperty = Main.quotesModel.quoteListProperty();
        Double priceChange = Math.round((listProperty.get(listProperty.getSize()-1).getPrice() - listProperty.get(0).getPrice())*100.0)/100.0;
        Main.quotesModel.setChange(priceChange/listProperty.get(listProperty.getSize()-1).getPrice()*100.0);

        lineChart.setTitle("BITCOIN");

        series.setName(arr.getDouble(arr.length()-1) + " USD");
        lineChart.setLegendSide(Side.TOP);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(24);
        xAxis.setTickUnit(6);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(min-100);
        yAxis.setUpperBound(max+100);
        yAxis.setTickUnit(100);

        lineChart.getData().add(series);

        lineChart.setCreateSymbols(false);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        initialize();
                    }
                });
            }
        }, 60000, 60000); // 60 seconds
    }
}
