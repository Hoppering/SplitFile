package com.meinekleinepupkin.splitfile.controller;

import com.meinekleinepupkin.splitfile.SplitApplication;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {


  @FXML
  protected void goToSplitPage(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplitApplication.class.getResource("split-view.fxml"));
    Parent root = fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  protected void goToJoinPage(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplitApplication.class.getResource("join-view.fxml"));
    Parent root = fxmlLoader.load();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

}
