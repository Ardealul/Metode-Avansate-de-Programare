package controller;

import com.sun.source.tree.CompoundAssignmentTree;
import domain.Entity;
import domain.Tema;
import domain.validators.ValidationException;
import events.ChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import sun.util.locale.StringTokenIterator;

import java.io.IOException;

public class TemaController implements Observer<ChangeEvent> {

    private Service service;
    private ObservableList<Tema> temaData = FXCollections.observableArrayList();
    private ObservableList<Tema> findData = FXCollections.observableArrayList();

    @FXML
    private TableView temaTableView;

    @FXML
    private TableColumn<Tema, String> tableColumnId;
    @FXML
    private TableColumn<Tema, String> tableColumnDescriere;
    @FXML
    private TableColumn<Tema, Integer> tableColumnStartWeek;
    @FXML
    private TableColumn<Tema, Integer> tableColumnDeadlineWeek;

    @FXML
    private TextField IdTextField;

    public void setService(Service service){
        this.service = service;
        temaData.setAll(service.getAllTema());
        service.addObserver(this);
        initialize();
    }

    private void initialize(){
        initializeTable();
    }

    private void initializeTable() {
        temaTableView.setEditable(true);
        initializeColumns(temaData);
        setColumnsTableEditable();
    }

    private void modifyTable(){
        temaTableView.setEditable(true);
        initializeColumns(findData);
        setColumnsTableEditable();
    }

    private void setColumnsTableEditable() {
        temaTableView.getSelectionModel().selectedItemProperty().addListener((x, y, z) ->{
            Tema tema = (Tema) z;
            if(tema != null){
                IdTextField.setText(tema.getId());
            }
        });
    }

    private void initializeColumns(ObservableList<Tema> list) {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<>("Descriere"));
        tableColumnDescriere.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnStartWeek.setCellValueFactory(new PropertyValueFactory<>("StartWeek"));
        tableColumnStartWeek.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableColumnDeadlineWeek.setCellValueFactory(new PropertyValueFactory<>("DeadlineWeek"));
        tableColumnDeadlineWeek.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        temaTableView.getColumns().set(0, tableColumnId);
        temaTableView.getColumns().set(1, tableColumnDescriere);
        temaTableView.getColumns().set(2, tableColumnStartWeek);
        temaTableView.getColumns().set(3, tableColumnDeadlineWeek);
        temaTableView.setItems(list);
    }

    private void loadTable(){
        temaData.setAll(service.getAllTema());
    }

    public void handleAdaugaButton(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/EditTemaView.fxml"));

            AnchorPane layout = loader.load();

            Stage stageAdauga = new Stage();
            stageAdauga.setTitle("TEME");
            stageAdauga.getIcons().add(new Image("/pics/tema1.png"));
            stageAdauga.initModality(Modality.WINDOW_MODAL);

            Scene sceneAdauga = new Scene(layout, 350, 400);
            stageAdauga.setScene(sceneAdauga);

            EditTemaController controller = loader.getController();
            controller.setService(service, stageAdauga, null);

            stageAdauga.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleStergeButton(ActionEvent actionEvent){
        try{
            String id = IdTextField.getText();

            service.deleteTema(id);
            EntityAlert.showMessage(null, Alert.AlertType.INFORMATION, "Stergere", "Tema a fost stearsa!");
            loadTable();
            IdTextField.clear();
        }
        catch (NumberFormatException | ValidationException e){
            EntityAlert.showErrorMessage(null, "Datele au fost introduse gresit!");
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Tema de sters nu exista!");
        }
    }

    public void handleModificaButton(ActionEvent actionEvent){
        try{
            Tema tema = (Tema) temaTableView.getSelectionModel().getSelectedItem();

            if(tema != null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/EditTemaView.fxml"));

                AnchorPane layout = loader.load();

                Stage stageAdauga = new Stage();
                stageAdauga.setTitle("TEME");
                stageAdauga.getIcons().add(new Image("/pics/tema1.png"));
                stageAdauga.initModality(Modality.WINDOW_MODAL);

                Scene sceneAdauga = new Scene(layout, 350, 400);
                stageAdauga.setScene(sceneAdauga);

                EditTemaController controller = loader.getController();
                controller.setService(service, stageAdauga, tema);

                stageAdauga.show();
            }
            else
                EntityAlert.showErrorMessage(null, "Nu a fost selectata nicio tema!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleGasesteButton(ActionEvent actionEvent){
        try{
            String id = IdTextField.getText();

            findData.setAll(service.findOneTema(id));
            modifyTable();
            IdTextField.clear();
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, "Datele au fost introduse gresit!");
        }
        catch (RepositoryException e){
            EntityAlert.showErrorMessage(null, "Tema nu a fost gasita!");
            initialize();
        }
        catch (IllegalArgumentException e){
            EntityAlert.showErrorMessage(null, "Id invalid!");
        }
    }

    public void handleGasesteToateTemeleButton(ActionEvent actionEvent){
        initialize();
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        loadTable();
    }
}
