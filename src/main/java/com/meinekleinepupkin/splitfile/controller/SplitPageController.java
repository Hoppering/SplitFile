package com.meinekleinepupkin.splitfile.controller;


import static com.meinekleinepupkin.splitfile.utils.FilesUtils.createFolderForSplitFile;

import com.meinekleinepupkin.splitfile.SplitApplication;
import com.meinekleinepupkin.splitfile.exception.NoContentException;
import com.meinekleinepupkin.splitfile.exception.NoContentTypeEnum;
import com.meinekleinepupkin.splitfile.service.Splitter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SplitPageController {

  @FXML
  private ComboBox<Integer> comboBox;
  @FXML
  private Button startButton;
  @FXML
  private Button closeButton;
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

  private File fileForSplit = new File("");
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
  protected void chooseFile(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    fileForSplit = fileChooser.showOpenDialog(stage);
    if (fileForSplit == null) {
      NoContentException.contentNotChosen(NoContentTypeEnum.FILE);
    } else {
      firstStageEllipse.setFill(colorReadyStage);
      secondStageEllipse.setFill(colorCurrentStage);
      firstStageLine.setStroke(colorReadyStage);
    }
    int i = 0;
    while(i < 20){
      i++;
      comboBox.getItems().add(i);
    }
    startButton.setDisable(false);
    comboBox.setDisable(false);
  }

  @FXML
  protected void splitFile(ActionEvent event) throws Exception {
    String pathFolder = createFolderForSplitFile(fileForSplit);
    Splitter.splitForManyFiles(pathFolder, fileForSplit, comboBox.getValue());
    secondStageLine.setStroke(colorReadyStage);
    secondStageEllipse.setFill(colorReadyStage);
    thirdStageEllipse.setFill(colorReadyStage);
    closeButton.setDisable(false);
  }
}
