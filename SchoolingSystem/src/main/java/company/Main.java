package company;

import domain.Nota;
import domain.StructuraAnUniversitar;
import domain.Student;
import domain.Tema;
import domain.validators.*;
import repository.xml.NotaXMLRepository;
import repository.xml.StudentXMLRepository;
import repository.xml.TemaXMLRepository;
import service.Service;
import ui.Consola;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws ValidationException {
        /*StructuraAnUniversitar structuraAnUniversitar = StructuraAnUniversitar.getInstance(
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

        Validator<Student> validatorStudent = new StudentValidator();
        Validator<Tema> validatorTema = new TemaValidator();
        Validator<Nota> validatorNota = new NotaValidator();

        //CrudRepository<String, Student> studentRepository = new StudentRepository(validatorStudent);
        //CrudRepository<String, Tema> temaRepository = new TemaRepository(validatorTema);

        //CrudRepository<String, Student> studentFileRepository = new StudentFileRepository(validatorStudent, "data/studenti.txt");
        //CrudRepository<String, Tema> temaFileRepository = new TemaFileRepository(validatorTema, "data/teme.txt");
        //CrudRepository<Pair<String, String>, Nota> notaFileRepository = new NotaFileRepository(validatorNota, "data/note.txt");


        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(new StudentValidator(), "studentiXML.xml");
        TemaXMLRepository temaXMLRepository = new TemaXMLRepository(new TemaValidator(), "temeXML.xml");
        NotaXMLRepository notaXMLRepository = new NotaXMLRepository(new NotaValidator(), "noteXML.xml");

        Service service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
        Consola consola = new Consola(service);
        //Consola.setCurrentWeek(1);
        Consola.setCurrentWeek(StructuraAnUniversitar.getInstance().getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1()));

        //System.out.println(StructuraAnUniversitar.getInstance().getWeek(LocalDate.of(2019,12,12), StructuraAnUniversitar.getInstance().getSem1()));

        consola.runMenu();*/

        MainApp.launch(args);
    }
}
