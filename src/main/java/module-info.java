module co.edu.uniquindio.poo.proyectocine {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.proyectocine to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectocine.model;
}
