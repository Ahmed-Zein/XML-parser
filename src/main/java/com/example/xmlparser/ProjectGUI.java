package com.example.xmlparser;

import helpers.LZW.Compressor;
import helpers.LZW.Decompressor;
import helpers.XmlConsistencyChecker;
import helpers.XmlMinifier;
import helpers.XmlTree;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ProjectGUI extends Application {
    File openedFile;
    TextArea leftTextArea = new TextArea();
    TextArea rightTextArea = new TextArea();

    @Override
    public void start(Stage stage) {

        leftTextArea.setPrefRowCount(30);
        rightTextArea.setPrefRowCount(30);
        leftTextArea.setPrefColumnCount(50);
        rightTextArea.setPrefColumnCount(50);
        leftTextArea.setWrapText(true);
        rightTextArea.setWrapText(true);

        Button openFileButton = new Button("Open File");
        Button checkConsistency = new Button("Check Consistency");
        Button minifyBtn = new Button("Minify");
        Button toJsonBtn = new Button("to Json");
        Button placeHolderBtn = new Button("placeHolder");
        Button compressorBtn = new Button("Compress");
        Button decompressBtn = new Button("Decompress");

        openFileButton.setOnAction(event -> openFile());
        checkConsistency.setOnAction(event -> checkConsistency());
        minifyBtn.setOnAction(event -> minify());
        toJsonBtn.setOnAction(event -> to_json());
        placeHolderBtn.setOnAction(event -> placeHolder());
        decompressBtn.setOnAction(event -> deCompress());
        compressorBtn.setOnAction(event -> compress());

        HBox utilBtnBox = new HBox(checkConsistency, minifyBtn, toJsonBtn, placeHolderBtn, compressorBtn, decompressBtn);
        VBox buttonBox = new VBox(openFileButton, utilBtnBox);
        HBox textBox = new HBox(leftTextArea, rightTextArea);
        VBox root = new VBox(textBox, buttonBox);

        root.setPadding(new Insets(10, 5, 10, 5));
        utilBtnBox.setPadding(new Insets(5, 0, 5, 0));
        buttonBox.setPadding(new Insets(5, 0, 5, 0));

        stage.setScene(new Scene(root, 720, 480));
        stage.setTitle("Text Area Example");
        stage.setResizable(false);
        stage.show();
    }

    private void isFileOpened() {
        if (openedFile == null)
            openFile();
        return;
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        openedFile = fileChooser.showOpenDialog(leftTextArea.getScene().getWindow());
        if (openedFile != null) {
            try {
                String contents = new String(Files.readAllBytes(openedFile.toPath()), StandardCharsets.UTF_8);
                leftTextArea.setText(contents);
            } catch (IOException e) {
                System.out.println("Canceled");
                System.out.println("=========================================");
                e.printStackTrace();
            }
        }
    }

    private void compress() {
        isFileOpened();
        Compressor compressor = new Compressor(openedFile);
        rightTextArea.setText(compressor.compress());
    }

    private void deCompress() {
        openFile();
        Decompressor deCompressor = new Decompressor(openedFile);
        rightTextArea.setText(deCompressor.deCompress());
    }


    private void checkConsistency() {
        isFileOpened();
        XmlConsistencyChecker checker = new XmlConsistencyChecker(openedFile);
        rightTextArea.clear();
        if (checker.checkConsistency())
            rightTextArea.setText("the file is consistent");
        else
            rightTextArea.setText("the file is not consistent");
    }

    private void minify() {
        isFileOpened();
        XmlMinifier minifier = new XmlMinifier(openedFile);
        try {
            rightTextArea.setText(minifier.minifyXml());
        } catch (Exception e) {
            e.printStackTrace();
            openFile();
        }
    }

    private void to_json() {
        isFileOpened();
        XmlTree xmlTree = null;
        try {
            xmlTree = new XmlTree(openedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            openFile();
        }
        rightTextArea.setText(xmlTree.toJson());
    }

    private void placeHolder() {
        rightTextArea.clear();
    }

    public static void main(String[] args) {
        launch();
    }
}