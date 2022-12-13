package ru.lamoda.datamatrix.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import ru.lamoda.datamatrix.gui.service.DatamatrixService;
import ru.lamoda.datamatrix.gui.model.DatamatrixResult;
import ru.lamoda.datamatrix.gui.ui.DatamatrixTableView;
import ru.lamoda.datamatrix.model.TradeGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatamatrixController {

    @FXML
    public Label summary;
    @FXML
    private GridPane root;
    @FXML
    private MenuButton tradeGroupSelect;
    @FXML
    private DatamatrixTableView dmTableView;

    private File source = null;
    private final List<TradeGroup> selectedTradeGroups = Arrays.stream(TradeGroup.values()).collect(Collectors.toList());
    private List<DatamatrixResult> result = new ArrayList<>();
    private final String tradeGroupSelectText = "Товарные группы (%s)";

    public void initialize() {
       initSelect();
       dmTableView.prefHeightProperty().bind(root.heightProperty());
       summary.setText(DatamatrixService.getSummaryText(result));
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".txt файлы", "*.txt"));
        source = fileChooser.showOpenDialog(root.getScene().getWindow());
        updateTableView();
    }

    @FXML
    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".txt файлы", "*.txt"),
                new FileChooser.ExtensionFilter(".csv файлы", "*.csv")
        );
        File selectedFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        DatamatrixService.saveResultToFile(result, selectedFile);
    }

    private void updateTableView() {
       result = DatamatrixService.getResult(source, selectedTradeGroups.toArray(TradeGroup[]::new));
       summary.setText(DatamatrixService.getSummaryText(result));
       dmTableView.getItems().setAll(result);
    }

    private void initSelect() {
        final List<CheckMenuItem> items = Arrays.stream(TradeGroup.values())
                .map(tradeGroup -> {
                    var item = new CheckMenuItem(tradeGroup.name());
                    item.setSelected(selectedTradeGroups.contains(tradeGroup));
                    return item;
                })
                .collect(Collectors.toList());
        tradeGroupSelect.getItems().addAll(items);
        tradeGroupSelect.setText(String.format(tradeGroupSelectText, selectedTradeGroups.size()));

        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue) {
                    selectedTradeGroups.add(TradeGroup.valueOf(item.getText()));
                } else {
                    selectedTradeGroups.remove(TradeGroup.valueOf(item.getText()));
                }
                tradeGroupSelect.setText(String.format(tradeGroupSelectText, selectedTradeGroups.size()));

                updateTableView();
            });
        }
    }
}
