package ru.lamoda.datamatrix.gui.ui;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import ru.lamoda.datamatrix.gui.model.DatamatrixResult;

public class DatamatrixTableView extends TableView<DatamatrixResult> {

    public DatamatrixTableView() {
        super();

        TableColumn<DatamatrixResult, Integer> index = new TableColumn<>( "№" );
        index.setCellFactory(new Callback<>() {
            @Override
            public TableCell<DatamatrixResult, Integer> call(TableColumn<DatamatrixResult, Integer> datamatrixResultIntegerTableColumn) {
                return new TableCell() {
                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(null);
                        setText(empty ? null : getIndex() + 1 + "" );
                    }
                };
            }
        });

        TableColumn<DatamatrixResult, String> code = new TableColumn<>("Датаматрикс код");
        code.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getDatamatrixCode()).getReadOnlyProperty());

        TableColumn<DatamatrixResult, String> validationResult = new TableColumn<>("Результат");
        validationResult.setCellValueFactory(p -> new ReadOnlyStringWrapper(p.getValue().getResultString()).getReadOnlyProperty());

        this.setRowFactory(row -> new TableRow<>() {
            @Override
            public void updateItem(DatamatrixResult item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (!item.getResult()) {
                    setStyle("-fx-background-color: red");
                } else {
                    setStyle("");
                }
            }
        });

        index.prefWidthProperty().bind(this.widthProperty().multiply(0.1));
        code.prefWidthProperty().bind(this.widthProperty().multiply(0.7));
        validationResult.prefWidthProperty().bind(this.widthProperty().multiply(0.2));

        this.getColumns().setAll(index, code, validationResult);
    }
}
