package edu.odu.msim.examples.svgpath;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class Example1 extends Application {

  @Override
  public void start(Stage stage) {
    final Group group = new Group();
    Scene scene = new Scene(group, 300, 150);
    stage.setScene(scene);
    stage.setTitle("Sample");

    SVGPath svg = new SVGPath();
    svg.setContent("M40,60 C42,48 44,30 25,32");
    
    group.getChildren().add(svg);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}