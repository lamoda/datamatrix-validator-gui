# Утилита валидации датаматрикс кодов

Реализована с использованием JavaFX и [библиотеки валидации даматрикс кодов](https://github.com/lamoda/datamatrix-validator).

![img.png](src/main/resources/img/main.png)

Сборка под Windows-платформу:
```
mvn clean install -Djavafx.platform=win
mvn javafx:run
```

Чтобы приложение можно было запустить без предустановленой java, положи jre в папку проекта /java/win/jre