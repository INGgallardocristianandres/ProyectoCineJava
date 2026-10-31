module co.edu.uniquindio.poo.proyectocine {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens co.edu.uniquindio.poo.proyectocine to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectocine.model;
}
