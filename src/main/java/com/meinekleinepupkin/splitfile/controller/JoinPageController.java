package com.meinekleinepupkin.splitfile.controller;

import com.meinekleinepupkin.splitfile.SplitApplication;
import com.meinekleinepupkin.splitfile.exception.NoContentException;
import com.meinekleinepupkin.splitfile.exception.NoContentTypeEnum;
import com.meinekleinepupkin.splitfile.service.Splitter;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class JoinPageController {

  @FXML
  private Ellipse firstStageEllipse;
  @FXML
  private Ellipse secondStageEllipse;
  @FXML
  private Ellipse thirdStageEllipse;
  @FXML
  private Line firstStageLine;
  @FXML
  private Line secondStageLine;
  @FXML
  private Button startButton;
  @FXML
  private Button closeButton;
  private File fileForJoin = new File("");
  private final Color colorReadyStage = Color.rgb(43, 152, 240);
  private final Color colorCurrentStage = Color.rgb(146, 193, 0);

  @FXML
  protected void goToHomePage(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplitApplication.class.getResource("home-view.fxml"));
    Parent root = fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void chooseDirectory(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    DirectoryChooser directoryChooser = new DirectoryChooser();
    fileForJoin = directoryChooser.showDialog(stage);
    if (fileForJoin == null) {
      NoContentException.contentNotChosen(NoContentTypeEnum.DIRECTORY);
    } else {
      firstStageEllipse.setFill(colorReadyStage);
      firstStageLine.setStroke(colorReadyStage);
      secondStageEllipse.setFill(colorCurrentStage);
      startButton.setDisable(false);
    }
  }

  @FXML
  protected void joinFilesToOne(ActionEvent event) throws Exception {
    Splitter.joinForManyFiles(fileForJoin.getAbsolutePath());
    secondStageEllipse.setFill(colorReadyStage);
    secondStageLine.setStroke(colorReadyStage);
    thirdStageEllipse.setFill(colorReadyStage);
    closeButton.setDisable(false);
  }
}
