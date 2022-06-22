module kino {
    requires javafx.controls;
    requires javafx.fxml;


    opens kino to javafx.fxml;
    exports kino;
}