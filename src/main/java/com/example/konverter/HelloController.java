package com.example.konverter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloController {

    private ScheduledExecutorService executor;

    private Integer status = 1;

    private Integer counter = 1;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static final DecimalFormat df4 = new DecimalFormat("0.0000");

    private Map<String, Double> currency;


    @FXML
    private TextField FirstCurrency;
    @FXML
    private TextField SecondCurrency;
    @FXML
    private ChoiceBox<String> firstCurrencyChoice;
    @FXML
    private ChoiceBox<String> secondCurrencyChoice;
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Label rateLabel;


    @FXML
    public void initialize () {

        currency = new HashMap<>();
        currency.put("Australian Dollar (AUD)", 0.64);
        currency.put("Bosnia-Herzegovina Convertible Mark (BAM)", 0.51);
        currency.put("Canadian Dollar (CAD)", 0.7);
        currency.put("Croatian Kuna (HRK)", 0.13);
        currency.put("Euro (EUR)", 1.0);
        currency.put("Ghanaian Cedi (GHS)", 0.073);
        currency.put("Hungarian Forint (HUF)", 0.0026);
        currency.put("Indian Rupee (INR)", 0.011);
        currency.put("Iraqi Dinar (IQD)", 0.00065);
        currency.put("Japanese Yen (JPY)", 0.0069);
        currency.put("Malaysian Ringgit (MYR)", 0.21);
        currency.put("Mexian Peso (MXN)", 0.051);
        currency.put("Nigerian Naira (NGN)", 0.0021);
        currency.put("Pakistan Rupee (PKR)", 0.0037);
        currency.put("Philippine Peso (PHP)", 0.017);
        currency.put("Pound Sterling (GBP)", 1.13);
        currency.put("Russian Ruble (RUB) ", 0.012);
        currency.put("Serbian Dinar (RSD)", 0.0085);
        currency.put("Singapore Dollar (SGD)", 0.70);
        currency.put("South African Rand (ZAR)", 0.051);
        currency.put("Thai Baht (THB)", 0.027);
        currency.put("United Arab Emirates Dirham (AED)", 0.26);
        currency.put("United States Dollar (USD)", 0.94);

        Set<String> setOfCurrencies = currency.keySet();
        List<String> listOfCurrencies = setOfCurrencies.stream().toList();

        ObservableList<String> currenciesObservableList = FXCollections.observableList(listOfCurrencies);
        firstCurrencyChoice.setItems(currenciesObservableList);
        secondCurrencyChoice.setItems(currenciesObservableList);


        FirstCurrency.setText("");
        SecondCurrency.setText("");

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(this::onPretvoriButtonClick);
        }, 0, 1, TimeUnit.MILLISECONDS);


    }

    @FXML
    private void onPretvoriButtonClick() {
        String firstCurrencyName = null;
        String secondCurrencyName = null;
        Double firstCurrencyValue = null;
        Double secondCurrencyValue = null;
        Double rate;
        if (!firstCurrencyChoice.getSelectionModel().isEmpty()) {
            firstCurrencyName = firstCurrencyChoice.getValue();
            String firstCurrencyShort = firstCurrencyName.substring(firstCurrencyName.length() - 5);
            firstCurrencyShort = firstCurrencyShort.replaceAll("[()]","");
            Label1.setText(firstCurrencyShort);
        }
        if (!secondCurrencyChoice.getSelectionModel().isEmpty()) {
            secondCurrencyName = secondCurrencyChoice.getValue();
            String secondCurrencyShort = secondCurrencyName.substring(secondCurrencyName.length() - 5);
            secondCurrencyShort = secondCurrencyShort.replaceAll("[()]","");
            Label2.setText(secondCurrencyShort);
        }
        if (!secondCurrencyChoice.getSelectionModel().isEmpty() && !firstCurrencyChoice.getSelectionModel().isEmpty()) {
            for (Map.Entry<String, Double> entry : currency.entrySet()) {
                if (entry.getKey().equals(firstCurrencyName)) {
                    firstCurrencyValue = entry.getValue();
                }
                if (entry.getKey().equals(secondCurrencyName)) {
                    secondCurrencyValue = entry.getValue();
                }
                if (firstCurrencyValue != null && secondCurrencyValue != null) {
                    rate = firstCurrencyValue/secondCurrencyValue;
                    String rateString = df4.format(rate);
                    rateString = rateString.replaceAll(",",".");
                    rateLabel.setText("The fixed conversion rate is " + rateString);
                    double firstValue = 0;
                    SecondCurrency.setText("");
                    try {
                        firstValue = Double.parseDouble(FirstCurrency.getText());
                    }catch (NumberFormatException ex) {

                    }
                    double secondValue = firstValue * rate;
                    String secondValueString = df.format(secondValue);
                    secondValueString = secondValueString.replaceAll(",",".");
                    SecondCurrency.setText(secondValueString);
                }
            }
        }
    }

    @FXML
    protected void onSwapButtonClick() {
        String firstCurrencyChoiceValue = firstCurrencyChoice.getValue();
        firstCurrencyChoice.setValue(secondCurrencyChoice.getValue());
        secondCurrencyChoice.setValue(firstCurrencyChoiceValue);
    }
}