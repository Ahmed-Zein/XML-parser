module com.example.xmlparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.xmlparser to javafx.fxml;
    exports com.example.xmlparser;
}