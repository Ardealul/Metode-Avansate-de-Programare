package controller;

import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.Student;
import domain.Tema;
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
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.*;
import observer.Observer;
import service.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NotaController implements Observer<ChangeEvent> {

    private Service service = null;
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Nota> notaData = FXCollections.observableArrayList();
    private ObservableList<Tema> temaData = FXCollections.observableArrayList();

    @FXML
    private TableView noteTableView;

    @FXML
    private ComboBox<Tema> temeComboBox;

    @FXML
    private TextField dataTextField;
    @FXML
    private TextField profesorTextField;
    @FXML
    private TextField valoareTextField;
    @FXML
    private TextField motivareTextField;
    @FXML
    private TextField intarziereTextField;
    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private Button adaugaButton;

    @FXML
    private TableColumn<Nota, String> idStudentColumn;
    @FXML
    private TableColumn<Nota, String> idTemaColumn;
    @FXML
    private TableColumn<Nota, LocalDate> dataColumn;
    @FXML
    private TableColumn<Nota, String> profesorColumn;
    @FXML
    private TableColumn<Nota, Float> valoareColumn;

    @FXML
    private TableView studentiTableView;

    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> numeColumn;
    @FXML
    private TableColumn<Student, String> prenumeColumn;

    @FXML
    private TextField findStudentTextField;


    public void setService(Service service){
        this.service = service;
        notaData.setAll(service.getAllNota());
        studentData.setAll(service.getAllStudent());
        temaData.setAll(service.getAllTema());
        service.addObserver(this);
        initialize();
    }

    private void initialize() {
        initializeTable();
        initializeComboBobx();
        dataTextField.setText("2019-12-10");
        profesorTextField.setText("Mariana");
        valoareTextField.setText("10");
        motivareTextField.setText("0");
        intarziereTextField.setText("0");
        feedbackTextArea.setText("felicitari, ai reusit sa-l predai!!! ");
    }

    private void initializeComboBobx() {
        temeComboBox.setItems(temaData);
        temeComboBox.setCellFactory(new Callback<ListView<Tema>, ListCell<Tema>>() {
            @Override
            public ListCell<Tema> call(ListView<Tema> param) {
                return new ListCell<Tema>() {
                    @Override
                    protected void updateItem(Tema item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getDescriere());
                        }
                    }
                };
            }
        });

        temeComboBox.setConverter(new StringConverter<Tema>() {
            @Override
            public String toString(Tema s) {
                if (s == null) {
                    return null;
                } else {
                    return s.getDescriere();
                }
            }
            @Override
            public Tema fromString(String studentString) {
                return null; // No conversion fromString needed.
            }
        });
        temeComboBox.getSelectionModel().selectLast();
    }

    private void initializeTable() {
        noteTableView.setEditable(false);
        studentiTableView.setEditable(false);
        initializeStudentTable(studentData);
        initiliazeNoteTable(notaData);
        setTableColumnsEditable();
    }

    private void initializeStudentTable(ObservableList<Student> list) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("Prenume"));
        prenumeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        studentiTableView.getColumns().set(0, idColumn);
        studentiTableView.getColumns().set(1, numeColumn);
        studentiTableView.getColumns().set(2, prenumeColumn);
        studentiTableView.setItems(list);

        findStudentTextField.textProperty().addListener(((observableValue, s, t1) -> handleFilter()));
    }

    private List<Student> getStudentList(){
        return service.getAllStudent()
                .stream()
                .map(n -> new Student(n.getId(), n.getNume(), n. getPrenume(), n.getEmail(), n.getGrupa(), n.getCadruDidacticIndrumatorLab()))
                .collect(Collectors.toList());
    }

    private void handleFilter() {
        Predicate<Student> filterNume = n -> (n.getNume().startsWith(findStudentTextField.getText()));

        studentData.setAll(getStudentList().stream().filter(filterNume).collect(Collectors.toList()));
    }

    private void setTableColumnsEditable() {

    }

    private void initiliazeNoteTable(ObservableList<Nota> list) {
        idStudentColumn.setCellValueFactory(new PropertyValueFactory<>("Student"));
        idStudentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idTemaColumn.setCellValueFactory(new PropertyValueFactory<>("Tema"));
        idTemaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("Data"));
        dataColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        profesorColumn.setCellValueFactory(new PropertyValueFactory<>("Profesor"));
        profesorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valoareColumn.setCellValueFactory(new PropertyValueFactory<>("Nota"));
        valoareColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        noteTableView.getColumns().set(0, idStudentColumn);
        noteTableView.getColumns().set(1, idTemaColumn);
        noteTableView.getColumns().set(2, dataColumn);
        noteTableView.getColumns().set(3, profesorColumn);
        noteTableView.getColumns().set(4, valoareColumn);
        noteTableView.setItems(list);
    }

    private void loadTable(){
        notaData.setAll(service.getAllNota());
    }

    public void handleAdaugaButton(ActionEvent actionEvent){
        try{
            LocalDate data = LocalDate.parse(dataTextField.getText());
            String profesor = profesorTextField.getText();
            Float nota = Float.parseFloat(valoareTextField.getText());
            if(nota < 1 || nota > 10){
                EntityAlert.showErrorMessage(null, "Nota trebuie sa fie intre 1 si 10!");
                return;
            }
            float motivare = Float.parseFloat(motivareTextField.getText());
            float intarziere = Float.parseFloat(intarziereTextField.getText());
            String feedback = feedbackTextArea.getText();

            Student s = (Student) studentiTableView.getSelectionModel().getSelectedItem();
            Tema t = temeComboBox.getSelectionModel().getSelectedItem();
            if(s == null){
                EntityAlert.showErrorMessage(null, "Nu a fost selectata niciun student!");
                return;
            }
            if(t == null){
                EntityAlert.showErrorMessage(null, "Nu a fost selectata nicio tema!");
                return;
            }

            Nota n = new Nota(new Pair<>(s.getId(), t.getId()), data, profesor, nota);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AddNotaView.fxml"));

            AnchorPane layout = loader.load();

            Stage stage = new Stage();
            stage.setTitle("CONFIRMARE NOTA");
            stage.getIcons().add(new Image("/pics/grade1.png"));
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(layout, 600,400);
            stage.setScene(scene);

            AddNotaController controller = loader.getController();
            controller.setService(service, stage, n, motivare, intarziere, feedback);

            stage.show();

        }
        catch (DateTimeParseException e){
           EntityAlert.showErrorMessage(null, "Data introdusa gresit!");
        }
        catch (ValidationException e){
            EntityAlert.showErrorMessage(null, "Introduceti datele cu grija!");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NumberFormatException e){
            EntityAlert.showErrorMessage(null, "Introduceti cu grija datele!");
        }
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        loadTable();
    }
}
