package com.meinekleinepupkin.splitfile.exception;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class NoContentException {

  public static Optional<ButtonType> contentNotChosen(NoContentTypeEnum type) {
    String errorText = switch (type) {
      case FILE -> "File";
      case DIRECTORY -> "Directory";
    };
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error Dialog");
    alert.setHeaderText(errorText + " didn't choose!");
    alert.setContentText("Ooops, but you forget to chose " + errorText + "!");
    return alert.showAndWait();
  }
}
