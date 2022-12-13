package ru.lamoda.datamatrix.gui.model;

import java.util.Objects;

public class DatamatrixResult {

    private final String datamatrixCode;
    private final Boolean result;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DatamatrixResult datamatrixResult = (DatamatrixResult) o;
        return result == datamatrixResult.result && datamatrixCode.equals(datamatrixResult.datamatrixCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datamatrixCode, result);
    }

    public DatamatrixResult(String datamatrixCode, Boolean result) {
        this.datamatrixCode = datamatrixCode;
        this.result = result;
    }

    public String getDatamatrixCode() {
        return datamatrixCode;
    }

    public String getResultString() {
        return result ? "Валидный КМ" : "Невалидный КМ";
    }

    public boolean getResult() {
        return result;
    }
}
