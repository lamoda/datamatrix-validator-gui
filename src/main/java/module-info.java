module ru.lamoda.datamatrix.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires datamatrix.validator;
    requires org.slf4j;

    opens ru.lamoda.datamatrix.gui to javafx.fxml;
    opens ru.lamoda.datamatrix.gui.controller to javafx.fxml;
    opens ru.lamoda.datamatrix.gui.ui to javafx.fxml;
    exports ru.lamoda.datamatrix.gui;
}