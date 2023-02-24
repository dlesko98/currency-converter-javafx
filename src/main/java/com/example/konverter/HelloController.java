package com.example.konverter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController {

    private ScheduledExecutorService executor;

    private Integer status = 1;

    private Integer counter = 1;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private TextField HRKTextField;
    @FXML
    private TextField EURTextField;
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Label Label3;
    @FXML
    private Label Label4;


    @FXML
    public void initialize () {
        HRKTextField.setText("0");

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(this::onPretvoriButtonClick);
        }, 0, 1, TimeUnit.MILLISECONDS);


        if (status == 1) {
            Label1.setText("Hrvatska kuna");
            Label2.setText("HRK");
            Label3.setText("Euro");
            Label4.setText("EUR");
        }
        else if (status == 2) {
            Label1.setText("Euro");
            Label2.setText("EUR");
            Label3.setText("Hrvatska kuna");
            Label4.setText("HRK");
        }

    }

    @FXML
    protected void onEnter () {
        onPretvoriButtonClick();
    }

    @FXML
    private void onPretvoriButtonClick() {
        if (status == 1) {
            double HRK = 0;
            EURTextField.setText("");
            try {
                HRK = Double.parseDouble(HRKTextField.getText());
            }catch (NumberFormatException ex) {

            }
            Double EUR = HRK / 7.53450;
            String EUR2 = df.format(EUR);
            EUR2 = EUR2.replaceAll(",",".");
            EURTextField.setText(EUR2);
        }
        else if (status == 2) {
            double HRK = 0;
            EURTextField.setText("");
            try {
                HRK = Double.parseDouble(HRKTextField.getText());
            }catch (NumberFormatException ex) {

            }
            Double EUR = HRK * 7.53450;
            String EUR2 = df.format(EUR);
            EUR2 = EUR2.replaceAll(",",".");
            EURTextField.setText(EUR2);
        }
    }

    @FXML
    protected void onSwapButtonClick() {
        if (status == 1) {
            status = 2;
        }
        else if (status == 2) {
            status = 1;
        }
        initialize();
    }
}