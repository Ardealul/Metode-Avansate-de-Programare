package controller;

import domain.Student;
import domain.StudentMedie;
import domain.TemaMedie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import service.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

public class RapoarteController {
    private Service service = null;
    private ObservableList<StudentMedie> studentiMedie = FXCollections.observableArrayList();
    private ObservableList<StudentMedie> studentiExamen =  FXCollections.observableArrayList();
    private ObservableList<Student> studentiSilitori =  FXCollections.observableArrayList();
    private ObservableList<TemaMedie> temeMedie =  FXCollections.observableArrayList();

    @FXML
    private TableView studentiExamenTableView;
    @FXML
    private TableColumn<StudentMedie, String> idStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, String> numeStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, Integer> grupaStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, Float> medieStudentiExamenTableColumn;

    @FXML
    private TableView studentiMedieTableView;
    @FXML
    private TableColumn<StudentMedie, String> idStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, String> numeStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, Integer> grupaStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, Float> medieStudentiMedieTableColumn;

    @FXML
    private TableView studentiSilitoriTableView;
    @FXML
    private TableColumn<Student, String> idStudentiSilitoriTableColumn;
    @FXML
    private TableColumn<Student, String> numeStudentiSilitoriTableColumn;
    @FXML
    private TableColumn<Student, Integer> grupaStudentiSilitoriTableColumn;

    @FXML
    private TableView temeMedieTableView;
    @FXML
    private TableColumn<TemaMedie, String> idTemeMedieTableColumn;
    @FXML
    private TableColumn<TemaMedie, String> descriereTemeMedieTableColumn;
    @FXML
    private TableColumn<TemaMedie, Float> medieTemeMedieTableColumn;

    public void setService(Service service){
        this.service = service;
        studentiMedie.setAll(service.getAllStudentiMedie());
        studentiExamen.setAll(service.getAllStudentiExamen());
        studentiSilitori.setAll(service.getAllStudentiSilitori());
        temeMedie.setAll(service.getAllTemeMedie());
        initialize();
    }

    private void initialize() {
        initializeTabels();
    }

    private void initializeTabels() {
        initializeStudentiMedie(studentiMedie);
        initializeStudentiExamen(studentiExamen);
        initializeStudentiSilitori(studentiSilitori);
        initializeTemeMedie(temeMedie);
    }

    private void initializeStudentiSilitori(ObservableList<Student> list) {
        idStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiSilitoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiSilitoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        studentiSilitoriTableView.getColumns().set(0, idStudentiSilitoriTableColumn);
        studentiSilitoriTableView.getColumns().set(1, numeStudentiSilitoriTableColumn);
        studentiSilitoriTableView.getColumns().set(2, grupaStudentiSilitoriTableColumn);
        studentiSilitoriTableView.setItems(list);
    }

    private void initializeTemeMedie(ObservableList<TemaMedie> list) {
        idTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        descriereTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Descriere"));
        descriereTemeMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        medieTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieTemeMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        temeMedieTableView.getColumns().set(0, idTemeMedieTableColumn);
        temeMedieTableView.getColumns().set(1, descriereTemeMedieTableColumn);
        temeMedieTableView.getColumns().set(2, medieTemeMedieTableColumn);
        temeMedieTableView.setItems(list);
        temeMedieTableView.getSelectionModel().selectFirst();
    }

    private void initializeStudentiExamen(ObservableList<StudentMedie> list) {
        idStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        medieStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        studentiExamenTableView.getColumns().set(0, idStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(1, numeStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(2, grupaStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(3, medieStudentiExamenTableColumn);
        studentiExamenTableView.setItems(list);
    }

    private void initializeStudentiMedie(ObservableList<StudentMedie> list) {
        idStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        medieStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        studentiMedieTableView.getColumns().set(0, idStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(1, numeStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(2, grupaStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(3, medieStudentiMedieTableColumn);
        studentiMedieTableView.setItems(list);
    }

    public void handleLabelStudentiMedie(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/BarChartView.fxml"));

            AnchorPane layout = loader.load();

            Stage stage = new Stage();
            stage.setTitle("STATISTICI");
            stage.getIcons().add(new Image("/pics/rapoarte.png"));
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(layout, 650, 500);
            stage.setScene(scene);

            BarChartController controller = loader.getController();
            controller.setService(service);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
