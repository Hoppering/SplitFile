module com.meinekleinepupkin.splitfile {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.meinekleinepupkin.splitfile to javafx.fxml;
  opens com.meinekleinepupkin.splitfile.controller to javafx.fxml;
  exports com.meinekleinepupkin.splitfile;
}