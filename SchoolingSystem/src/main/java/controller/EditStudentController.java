package controller;

import domain.Student;
import domain.validators.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;

public class EditStudentController {

    private Service service = null;
    Stage stage;
    Student student;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField numeTextField;
    @FXML
    private TextField prenumeTextField;
    @FXML
    private TextField grupaTextField;
    @FXML
    private TextField profesorTextField;
    @FXML
    private TextField emailTextField;

    public void setService(Service service, Stage stage, Student student){
        this.service = service;
        this.stage = stage;
        this.student = student;
        if(student != null){
            setFields(student);
            idTextField.setDisable(true);
        }
    }

    private void setFields(Student student){
        idTextField.setText(student.getId());
        numeTextField.setText(student.getNume());
        prenumeTextField.setText(student.getPrenume());
        grupaTextField.setText(student.getGrupa().toString());
        profesorTextField.setText(student.getCadruDidacticIndrumatorLab());
        emailTextField.setText(student.getEmail());
    }

    @FXML
    public void handleSaveButton(ActionEvent actionEvent){
        try {
            String id = idTextField.getText();
            String nume = numeTextField.getText();
            String prenume = prenumeTextField.getText();
            Integer grupa = Integer.parseInt(grupaTextField.getText());
            String profesor = profesorTextField.getText();
            String email = emailTextField.getText();

            if (this.student == null) {
                adaugaStudent(id, nume, prenume, email, grupa, profesor);
            } else
                modificaStudent(id, nume, prenume, email, grupa, profesor);
        }
        catch (NumberFormatException e){
            EntityAlert.showErrorMessage(null, "Introduceti datele cu grija!");
        }
    }

    private void adaugaStudent(String id, String nume, String prenume, String email, Integer grupa, String profesor){
        try{
            Student s = service.saveStudent(id, nume, prenume, email, grupa, profesor);
            if(s == null){
                stage.close();
                EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare", "Studentul a fost adaugat!");
            }
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, e.getMessage());
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Studentul exista deja!");
        }
    }

    private void modificaStudent(String id, String nume, String prenume, String email, Integer grupa, String profesor){
        try{
            Student s = service.updateStudent(id, nume, prenume, email, grupa, profesor);
            if(s == null){
                stage.close();
                EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Modificare", "Studentul a fost modificat!");
            }
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, e.getMessage());
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Studentul de modificat nu a fost gasit!");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent actionEvent){
        stage.close();
    }
}
