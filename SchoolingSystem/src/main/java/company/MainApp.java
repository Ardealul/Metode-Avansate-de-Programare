package company;

import configuration.ApplicationContext;
import controller.Controller;
import domain.validators.NotaValidator;
import domain.validators.StudentValidator;
import domain.validators.TemaValidator;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import repository.xml.NotaXMLRepository;
import repository.xml.StudentXMLRepository;
import repository.xml.TemaXMLRepository;
import service.Service;
import utils.Paths;

import java.io.IOException;

public class MainApp extends Application {

    StudentXMLRepository studentXMLRepository;
    TemaXMLRepository temaXMLRepository;
    NotaXMLRepository notaXMLRepository;
    Service service;

    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage primaryStage) throws IOException {
        studentXMLRepository = new StudentXMLRepository(new StudentValidator(), ApplicationContext.getPROPERTIES().getProperty("data.studenti")); //Paths.STUDENT
        temaXMLRepository = new TemaXMLRepository(new TemaValidator(), ApplicationContext.getPROPERTIES().getProperty("data.teme"));
        notaXMLRepository = new NotaXMLRepository(new NotaValidator(), ApplicationContext.getPROPERTIES().getProperty("data.note"));

        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);

        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("/views/StudentView.fxml"));
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        //loader.setLocation(getClass().getResource("/views/EditStudentView.fxml"));

        AnchorPane layout = loader.load();

        //controller
        Controller controller = loader.getController();
        controller.setService(service);

        Scene scene = new Scene(layout, 600,375);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JADOR");
        primaryStage.getIcons().add(new Image("/pics/jadorelu.png"));
        primaryStage.show();
    }
}
