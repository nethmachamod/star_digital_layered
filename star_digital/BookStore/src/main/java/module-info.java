module lk.ijse.star_digitalBook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires net.sf.jasperreports.core;



    opens lk.ijse.star_digitalBook.dto to javafx.base;

    opens lk.ijse.star_digitalBook to javafx.fxml;
    opens lk.ijse.star_digitalBook.controller to javafx.fxml;
    exports lk.ijse.star_digitalBook;
}
