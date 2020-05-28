package controller;

import domain.Tema;
import domain.validators.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;

public class EditTemaController {

    private Service service = null;
    Stage stage;
    Tema tema;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField descriereTextField;
    @FXML
    private TextField startWeekTextField;
    @FXML
    private TextField deadlineWeekTextField;

    public void setService(Service service, Stage stage, Tema tema){
        this.service = service;
        this.stage = stage;
        this.tema = tema;
        if(tema != null){
            setFields(tema);
            idTextField.setDisable(true);
        }
    }

    private void setFields(Tema tema) {
        idTextField.setText(tema.getId());
        descriereTextField.setText(tema.getDescriere());
        startWeekTextField.setText(tema.getStartWeek().toString());
        deadlineWeekTextField.setText(tema.getDeadlineWeek().toString());
    }

    @FXML
    public void handleSaveButton(ActionEvent actionEvent){
        try{
            String id =  idTextField.getText();
            String descriere = descriereTextField.getText();
            Integer startWeek = Integer.parseInt(startWeekTextField.getText());
            Integer deadlineWeek = Integer.parseInt(deadlineWeekTextField.getText());

            if(this.tema == null)
                adaugaTema(id, descriere, startWeek, deadlineWeek);
            else
                modificaTema(id, descriere, startWeek, deadlineWeek);
        }
        catch (NumberFormatException e){
            EntityAlert.showErrorMessage(null, "Introduceti datele cu grija!");
        }
    }

    private void adaugaTema(String id, String descriere, Integer startWeek, Integer deadlineWeek) {
        try{
            Tema t = service.saveTema(id, descriere, startWeek, deadlineWeek);
            if(t == null){
                stage.close();
                EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare", "Tema a fost adaugata!");
            }
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, e.getMessage());
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Tema exista deja!");
        }
    }

    private void modificaTema(String id, String descriere, Integer startWeek, Integer deadlineWeek) {
        try{
            Tema t = service.updateTema(id, descriere, startWeek, deadlineWeek);
            if(t == null){
                stage.close();
                EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Modificare", "Tema a fost modificata!");
            }
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, e.getMessage());
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Tema de modificat nu a fost gasita!");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent actionEvent){
        stage.close();
    }
}
