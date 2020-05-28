package controller;


import domain.Student;
import domain.validators.ValidationException;
import events.ChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import observer.Observer;
import repository.RepositoryException;
import service.Service;

import java.io.IOException;

public class StudentController implements Observer<ChangeEvent>{

    private Service service;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Student> findData = FXCollections.observableArrayList();
    private Student editableStudent = null;

    //tableView
    @FXML
    private TableView studentTableView;

    //tableColumn
    @FXML
    private TableColumn<Student, String> tableColumnId;
    @FXML
    private TableColumn<Student, String> tableColumnNume;
    @FXML
    private TableColumn<Student, String> tableColumnPrenume;
    @FXML
    private TableColumn<Student, Integer> tableColumnGrupa;
    @FXML
    private TableColumn<Student, String> tableColumnProfesor;
    @FXML
    private TableColumn<Student, String> tableColumnEmail;

    //textField
    @FXML
    private TextField IdTextField;


    private void initialize(){
        initializeTable();
    }

    public void setService(Service service){
        this.service = service;
        studentData.setAll(service.getAllStudent());
        service.addObserver(this);
        initialize();
    }

    private void initializeTable(){
        studentTableView.setEditable(false);
        initializeColumns(studentData);
        setTableColumnsEditable();
    }

    private void modifyTable(){
        studentTableView.setEditable(true);
        initializeColumns(findData);
        setTableColumnsEditable();
    }

    private void setTableColumnsEditable(){
        studentTableView.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> {
            Student student = (Student) z;
            if(student != null) {
                IdTextField.setText(student.getId());
            }
        });
    }

    private void initializeColumns(ObservableList<Student> list){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        tableColumnNume.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<>("Prenume"));
        tableColumnPrenume.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        tableColumnGrupa.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<>("CadruDidacticIndrumatorLab"));
        tableColumnProfesor.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tableColumnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        studentTableView.getColumns().set(0, tableColumnId);
        studentTableView.getColumns().set(1, tableColumnNume);
        studentTableView.getColumns().set(2, tableColumnPrenume);
        studentTableView.getColumns().set(3, tableColumnGrupa);
        studentTableView.getColumns().set(4, tableColumnProfesor);
        studentTableView.getColumns().set(5, tableColumnEmail);
        studentTableView.setItems(list);
    }

    private void loadTable(){
        studentData.setAll(service.getAllStudent());
    }

    //
    public void handleAdaugaButton(ActionEvent actionEvent){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/EditStudentView.fxml"));

            AnchorPane layout = loader.load();

            Stage stageAdauga = new Stage();
            stageAdauga.setTitle("STUDENTI");
            stageAdauga.getIcons().add(new Image("/pics/student2.png"));
            stageAdauga.initModality(Modality.WINDOW_MODAL);

            Scene sceneAdauga = new Scene(layout, 350, 400);
            stageAdauga.setScene(sceneAdauga);

            EditStudentController controller = loader.getController();
            controller.setService(service, stageAdauga, null);

            stageAdauga.show();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleStergeButton(ActionEvent actionEvent){
        try {
            String id = IdTextField.getText();

            service.deleteStudent(id);
            EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Stergere", "Studentul a fost sters!");
            loadTable();
            IdTextField.clear();
        }
        catch (NumberFormatException | ValidationException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Datele au fost introduse gresit!");
            alert.show();
        }
        catch (RepositoryException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Studentul de sters nu exista!");
            alert.show();
        }
    }

    public void handleModificaButton(ActionEvent actionEvent){
        try {

            Student student = (Student) studentTableView.getSelectionModel().getSelectedItem();

            if(student != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/EditStudentView.fxml"));

                AnchorPane layout = loader.load();

                Stage stageAdauga = new Stage();
                stageAdauga.setTitle("STUDENTI");
                stageAdauga.getIcons().add(new Image("/pics/student2.png"));
                stageAdauga.initModality(Modality.WINDOW_MODAL);

                Scene sceneAdauga = new Scene(layout, 350, 400);
                stageAdauga.setScene(sceneAdauga);

                EditStudentController controller = loader.getController();
                controller.setService(service, stageAdauga, student);

                stageAdauga.show();
            }
            else
                EntityAlert.showErrorMessage(null, "Nu a fost selectat niciun student!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleGasesteButton(ActionEvent actionEvent){
        try{
            String id = IdTextField.getText();

            //Student student = service.findOneStudent(id);

            findData.setAll(service.findOneStudent(id));
            modifyTable();

            IdTextField.clear();
        }
        catch (ValidationException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Datele au fost introduse gresit!");
            alert.show();
        }
        catch (RepositoryException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Studentul nu a fost gasit!");
            alert.show();
            initialize();
        }
        catch (IllegalArgumentException e){
            EntityAlert.showErrorMessage(null, "Id invalid!");
        }
    }

    public void handleGasesteTotiStudentiiButton(ActionEvent actionEvent){
        initialize();
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        loadTable();
    }
}
