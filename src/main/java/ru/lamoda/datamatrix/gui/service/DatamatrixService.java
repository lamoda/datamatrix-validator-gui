package ru.lamoda.datamatrix.gui.service;

import javafx.scene.control.Alert;
import ru.lamoda.datamatrix.gui.model.DatamatrixResult;
import ru.lamoda.datamatrix.model.TradeGroup;
import ru.lamoda.datamatrix.validation.DatamatrixValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatamatrixService {

    private static final Logger logger = LoggerFactory.getLogger(DatamatrixService.class);

    public static String getSummaryText(List<DatamatrixResult> result) {
        int badDm = (int) result.stream().filter(Predicate.not(DatamatrixResult::getResult)).count();
        return String.format( "Всего КМ: %s\nПроблемных КМ: %s", result.size(), badDm);
    }

    public static void saveResultToFile(List<DatamatrixResult> result, File file) {
        if (file != null) {
            var data = result.stream()
                    .map(v -> String.format("%s\t%s", v.getDatamatrixCode(), v.getResultString()))
                    .collect(Collectors.joining("\n"));
            try {
                Files.writeString(file.toPath(), data);
            } catch (IOException e) {
                logger.error("Can't save file: ", e);
                new Alert(Alert.AlertType.ERROR, "Can't save file: " + e.getMessage()).showAndWait();
            }
        }
    }

    public static List<DatamatrixResult> getResult(File file, TradeGroup[] selectedTradeGroups) {
        List<DatamatrixResult> result = new ArrayList<>();
        try {
            if (file != null) {
                result = Files.lines(file.toPath())
                        .map(datamatrix -> new DatamatrixResult(datamatrix,
                                DatamatrixValidator.validate(datamatrix, selectedTradeGroups)))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            logger.error("Can't process file: ", e);
            new Alert(Alert.AlertType.ERROR, "Can't process file: " + e.getMessage()).showAndWait();
        }
        return result;
    }
}
