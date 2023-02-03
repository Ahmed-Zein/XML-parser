package com.example.xmlparser.stages;

import helpers.NetworkAnalysis.Network;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.User;

import java.io.File;
import java.util.ArrayList;

public class AnalyzerStage {
    public Stage getAnalyzerStage() {
        return analyzerStage;
    }

    private Stage analyzerStage;
    private File tFile;

    public AnalyzerStage(File file) {
        analyzerStage = new Stage();
        this.tFile = file;
    }

    public void start() {
         Network net = new Network(tFile);
        ArrayList<ArrayList<Integer>> adjList = net.getAdjacencyMatrix();

        /* Initializing the container for the GUI */
        VBox container = new VBox();
        container.setSpacing(20); // Adding some space between the elements
        container.setPadding(new Insets(20)); // Adding some padding around the elements

        // Adding all the elements to the container
        for (int j = 0; j < adjList.size(); j++) {
            HBox hBox = new HBox();
            hBox.setSpacing(20); // Adding some space between the elements
            ArrayList<Integer> row = adjList.get(j);
            for (int i = 0; i < adjList.get(j).size(); i++) {
                GridPane gridPane = new GridPane();
                gridPane.setAlignment(Pos.CENTER);
                gridPane.add(new Label(row.get(i).toString()), i, 0);
                hBox.getChildren().add(gridPane);
            }
            if (net.users.get(j + 1) != null) {
                hBox.getChildren().add(new Label(net.users.get(j + 1).getName()));
            }
            container.getChildren().add(hBox);
        }

        /* Adding the results of the most influencer and most active users */
        VBox resultVBox = new VBox();
        resultVBox.setSpacing(10);
        resultVBox.setPadding(new Insets(20));
        resultVBox.getChildren().add(new Label("The most influencer user :\t" + net.getMostInference().getName() + "\t with id :\t" + net.getMostInference().getId()));
        resultVBox.getChildren().add(new Label("The most active user:\t" + net.getMostActive().getName() + "\t with id :\t" + net.getMostActive().getId()));
        container.getChildren().add(resultVBox);

        Scene scene = new Scene(container, 460, 460);
        analyzerStage.setScene(scene);
        analyzerStage.show();
    }
}
