package controller;

import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.StructuraAnUniversitar;
import domain.Student;
import domain.Tema;
import domain.validators.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;

import java.time.LocalDate;

public class AddNotaController {
    private Service service = null;
    Stage stage;
    Nota nota;
    String informatiiNota;
    StructuraAnUniversitar structuraAnUniversitar = StructuraAnUniversitar.getInstance(
            "1",
            1,
            1,
            LocalDate.of(2019, 9, 30),
            LocalDate.of(2019, 12, 23),
            LocalDate.of(2020, 1, 5),
            LocalDate.of(2020, 1, 17),
            2,
            LocalDate.of(2020, 2, 24),
            LocalDate.of(2020, 4, 17),
            LocalDate.of(2020, 4, 27),
            LocalDate.of(2020, 6, 5));

    float motivare;
    float intarziere;
    String feedback;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextArea informatiiNotaTextArea;

    public void setService(Service service, Stage stage, Nota nota, float motivare, float intarziere, String feedback){
        this.service = service;
        this.stage = stage;
        this.nota = nota;
        this.motivare = motivare;
        this.intarziere = intarziere;
        this.feedback = feedback;
        setTextArea();
    }

    public void setTextArea(){
        Float valoareFinala = service.getNotaFinala(nota.getId().fst, nota.getId().snd, nota.getData(), nota.getNota(), motivare, intarziere);
        informatiiNotaTextArea.setText("Student: " + service.findOneStudent(nota.getId().fst).getNume() + " " + service.findOneStudent(nota.getId().fst).getPrenume()
                + "\nProfesor: " + nota.getProfesor()
                + "\nTema: " +service.findOneTema(nota.getId().snd).getDescriere()
                + "\nData: " + nota.getData().toString()
                + "\nNota acordata de profesor: " + nota.getNota().toString()
                + "\nNr sapt motivare: " + (int)motivare
                + "\nNr sapt intarziere profesor: " + (int)intarziere
                + "\nNota finala este "
                + valoareFinala
                + "\nFeedback: " + feedback);
        //service.deleteNota(nota.getId());
    }

    @FXML
    public void handleOkButton(ActionEvent actionEvent){
        try{
            Nota n = service.saveNota(nota.getId().fst, nota.getId().snd, nota.getData(), nota.getProfesor(), nota.getNota(), motivare, intarziere, feedback);
            if(n == null){
                EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare", "Nota a fost salvata!");
            }
            else
                EntityAlert.showErrorMessage(null, "O nota a fost deja asignata studentului si temei!");
            stage.close();
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, "Introduceti corect datele!");
        }
        catch (IllegalArgumentException e){
            EntityAlert.showErrorMessage(null, "Introduceti corect datele!");
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Nota este deja salvata!");
        }
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent){
        stage.close();
    }
}
