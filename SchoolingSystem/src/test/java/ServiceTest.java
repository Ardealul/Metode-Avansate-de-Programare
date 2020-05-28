import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validators.*;
import fileRepository.NotaFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.RepositoryException;
import repository.StudentRepository;
import repository.TemaRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServiceTest {

    Student s;
    Tema t;
    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();
    CrudRepository<String, Student> studentRepository;
    CrudRepository<String, Tema> temaRepository;
    CrudRepository<Pair<String, String>, Nota> notaRepository;
    //crudRepository pt nota bla bla
    Service service;

    @BeforeEach
    public void setUp() throws Exception{
        studentRepository = new StudentRepository(studentValidator);
        temaRepository = new TemaRepository(temaValidator);
        notaRepository = new NotaFileRepository(notaValidator, "data/teste.txt");
        service = new Service(studentRepository, temaRepository, notaRepository);
        service.saveStudent("1", "Barbu", "Ion", "bi@yahoo.com", 226, "Andreea Vescan");
        service.saveTema("1","laborator5", 8, 9);
    }

    @Test
    public void findOneStudentTrue(){
        assertEquals("bi@yahoo.com", service.findOneStudent("1").getEmail());
    }

    @Test
    public void findOneStudentIllegalArgument(){
        assertThrows(IllegalArgumentException.class, () ->{
            service.findOneStudent("");
        });
    }

    @Test
    public void findOneStudentRepoException(){
        assertThrows(RepositoryException.class, ()->{
           service.findOneStudent("100");
        });
    }

    @Test
    public void findAllStudent(){
        int size = 0;
        for(Student s : service.findAllStudent()){
            size++;
        }
        assertEquals(1, size);
    }


    @Test
    public void saveStudentValidationException(){
        assertThrows(ValidationException.class, ()->{
           service.saveStudent("", "", "", "", 221, "");
        });
    }


    @Test
    public void deleteStudentTrue(){
        assertEquals("Barbu", service.deleteStudent("1").getNume());
    }


    @Test
    public void updateStudentRepositoryException(){
        assertThrows(RepositoryException.class, ()->{
            service.updateStudent("2","Gregorian", "Patricia", "gp@yahoo.com", 226, "Vescan Andreea");
        });
    }

    @Test
    public void updateStudentValidationException(){
        assertThrows(ValidationException.class, ()->{
           service.updateStudent("1", "","","",225,"");
        });
    }


    @Test
    public void findOneTemaIllegalArgument(){
        assertThrows(IllegalArgumentException.class, ()->{
           service.findOneTema("");
        });
    }

    @Test
    public void findOneTemaRepoException(){
        assertThrows(RepositoryException.class, ()->{
           service.findOneTema("100");
        });
    }

    @Test
    public void findAllTema(){
        int size = 0;
        for (Tema t: service.findAllTema()){
            size++;
        }
        assertEquals(1, size);
    }


    @Test
    public void saveTemaValidationException(){
        assertThrows(ValidationException.class, ()->{
           service.saveTema("5", "", -3, 18);
        });
    }



    @Test
    public void updateTema(){
        assertThrows(NullPointerException.class, ()->{
           service.updateTema("100","lab5", 9, 10);
        });
    }

    @Test
    public void filtrareStudentiGrupa(){
        Iterable<Student> filtrare = service.getStudentByGrupa(226);
        int size = 0;
        for(Student st : filtrare){
            size++;
        }
        assertEquals(1, size);
    }
}
