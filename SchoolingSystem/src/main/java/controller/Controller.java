package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class Controller {
    private Service service = null;

    @FXML
    private ImageView studentImage;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button studentiButton;
    @FXML
    private Button temeButton;
    @FXML
    private Button noteButton;
    @FXML
    private Button rapoarteButton;

    @FXML
    private Label titleLabel;

    public void setService(Service service){
        this.service = service;
        initialize();
    }

    private void initialize(){};

    public void handleStudentiButton(){
        try{
        FXMLLoader studentiLoader = new FXMLLoader();
        studentiLoader.setLocation(getClass().getResource("/views/StudentView.fxml"));

        AnchorPane layout = studentiLoader.load();

        Stage studentiStage = new Stage();
        studentiStage.setTitle("STUDENTI");
        studentiStage.getIcons().add(new Image("/pics/student2.png"));
        studentiStage.initModality(Modality.WINDOW_MODAL);

        Scene studentiScene = new Scene(layout, 550,600);
        studentiStage.setScene(studentiScene);

        StudentController studentController = studentiLoader.getController();
        studentController.setService(service);

        studentiStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleTemeButton(){
        try{
            FXMLLoader temeLoader = new FXMLLoader();
            temeLoader.setLocation(getClass().getResource("/views/TemaView.fxml"));

            AnchorPane layout = temeLoader.load();

            Stage temeStage = new Stage();
            temeStage.setTitle("TEME");
            temeStage.getIcons().add(new Image("/pics/tema1.png"));
            temeStage.initModality(Modality.WINDOW_MODAL);

            Scene temeScene = new Scene(layout, 550,600);
            temeStage.setScene(temeScene);

            TemaController temaController = temeLoader.getController();
            temaController.setService(service);

            temeStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleNoteButton(){
        try{
            FXMLLoader noteLoader = new FXMLLoader();
            noteLoader.setLocation(getClass().getResource("/views/NotaView.fxml"));

            AnchorPane layout = noteLoader.load();

            Stage noteStage = new Stage();
            noteStage.setTitle("NOTE");
            noteStage.getIcons().add(new Image("/pics/grade1.png"));
            noteStage.initModality(Modality.WINDOW_MODAL);

            Scene noteScene = new Scene(layout, 850, 650);
            noteStage.setScene(noteScene);

            NotaController notaController = noteLoader.getController();
            notaController.setService(service);

            noteStage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleRapoarteButton(){
        try{
            FXMLLoader noteLoader = new FXMLLoader();
            noteLoader.setLocation(getClass().getResource("/views/RapoarteView.fxml"));

            AnchorPane layout = noteLoader.load();

            Stage rapoarteStage = new Stage();
            rapoarteStage.setTitle("RAPOARTE");
            rapoarteStage.getIcons().add(new Image("/pics/rapoarte.png"));
            rapoarteStage.initModality(Modality.WINDOW_MODAL);

            Scene rapoarteScene = new Scene(layout, 800, 600);
            rapoarteStage.setScene(rapoarteScene);

            RapoarteController rapoarteController = noteLoader.getController();
            rapoarteController.setService(service);

            rapoarteStage.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
