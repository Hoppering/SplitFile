package com.meinekleinepupkin.splitfile.controller;


import static com.meinekleinepupkin.splitfile.utils.FilesUtils.createFolderForSplitFile;
import static com.meinekleinepupkin.splitfile.utils.FilesUtils.removeExtension;

import com.meinekleinepupkin.splitfile.SplitApplication;
import com.meinekleinepupkin.splitfile.exception.NoContentException;
import com.meinekleinepupkin.splitfile.exception.NoContentTypeEnum;
import com.meinekleinepupkin.splitfile.service.Splitter;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
  private Button testButton;
  @FXML
  private Button startButton;
  @FXML
  private Button closeButton;
  @FXML
  private Ellipse ellipseFirstStage;
  @FXML
  private Ellipse ellipseSecondStage;
  @FXML
  private Ellipse ellipseThirdStage;
  @FXML
  private Ellipse ellipseForthStage;
  @FXML
  private Line lineFirstStage;
  @FXML
  private Line lineSecondStage;
  @FXML
  private Line lineThirdStage;

  private File fileForSplit = new File("");
  private final Color colorReadyStage = Color.rgb(43, 152, 240);

  @FXML
  protected void goToHomePage(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplitApplication.class.getResource("home-view.fxml"));
    Parent root = (Parent) fxmlLoader.load();
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
      testButton.setDisable(false);
      ellipseFirstStage.setFill(colorReadyStage);
      lineFirstStage.setStroke(colorReadyStage);
    }
  }

  @FXML
  protected void testSplitFile(ActionEvent event) throws Exception {
    List<Integer> possiblyAmount = Splitter.checkPossiblyAmount(fileForSplit);
    for (Integer item : possiblyAmount) {
      comboBox.getItems().add(item);
    }
    startButton.setDisable(false);
    comboBox.setDisable(false);
    ellipseSecondStage.setFill(colorReadyStage);
    lineSecondStage.setStroke(colorReadyStage);
  }

  @FXML
  protected void splitFile(ActionEvent event) throws Exception {
    String pathFolder = createFolderForSplitFile(removeExtension(fileForSplit.getName()));
    Splitter.splitForManyFiles(pathFolder, fileForSplit, comboBox.getValue());
    ellipseThirdStage.setFill(colorReadyStage);
    lineThirdStage.setStroke(colorReadyStage);
    ellipseForthStage.setFill(colorReadyStage);
    closeButton.setDisable(false);
  }
}
