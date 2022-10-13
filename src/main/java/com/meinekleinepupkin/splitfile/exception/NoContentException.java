package com.meinekleinepupkin.splitfile.exception;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NoContentException {

  public static void contentNotChosen(NoContentTypeEnum type) {
    String errorText = switch (type) {
      case FILE -> "File";
      case DIRECTORY -> "Directory";
    };
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error Dialog");
    alert.setHeaderText(errorText + " didn't choose!");
    alert.setContentText("Ooops, but you forget to chose " + errorText + "!");
    alert.showAndWait();
  }
}
