module com.example.database_final_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens com.example.database_final_javafx to javafx.fxml;
    opens com.example.database_final_javafx.entity to javafx.base;
    opens com.example.database_final_javafx.controller to javafx.fxml;
    opens com.example.database_final_javafx.DAO to javafx.fxml;
    opens com.example.database_final_javafx.utils to javafx.fxml;

    exports com.example.database_final_javafx;
    exports com.example.database_final_javafx.utils;
}