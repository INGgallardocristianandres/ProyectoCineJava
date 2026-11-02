module co.edu.uniquindio.poo.proyectocine {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    exports co.edu.uniquindio.poo.proyectocine to javafx.graphics;
    exports co.edu.uniquindio.poo.proyectocine.model;
    opens co.edu.uniquindio.poo.proyectocine.viewController to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectocine.controller to javafx.fxml;
}
