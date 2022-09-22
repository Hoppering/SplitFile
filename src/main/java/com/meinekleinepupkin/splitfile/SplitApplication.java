package com.meinekleinepupkin.splitfile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SplitApplication extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SplitApplication.class.getResource("home-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Split File");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}