import domain.Student;
import domain.validators.StudentValidator;
import domain.validators.ValidationException;
import domain.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.RepositoryException;
import repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryRepoTest {
    Student s;
    CrudRepository<String, Student> studentRepository;

    @BeforeEach
    public void setUp() throws Exception{
        Validator<Student> validatorStudent = new StudentValidator();
        studentRepository = new StudentRepository(validatorStudent);

        s = new Student("1", "Cailean", "Marius","caileanmarius@gmail.com", 221, "Serban Camelia");
        studentRepository.save(s);
    }

    @Test
    public void findOne(){
        assertEquals("Cailean", studentRepository.findOne("1").getNume());
        assertThrows(IllegalArgumentException.class, () -> {
           studentRepository.findOne("");
        });
    }

    @Test
    public void findAll(){
        int size = 0;
        for(Student s : studentRepository.findAll()){
            size++;
        }
        assertEquals(1, size);
    }

    @Test
    public void save(){
        Student s2 = new Student("2", "Cadis", "Paul", "cadispaul@yahoo.com", 222, "Vescan Andreea");
        assertThrows(IllegalArgumentException.class, () ->{
           studentRepository.save(null);
        });
        assertThrows(ValidationException.class, ()->{
           Student s3 = new Student("","","","",221,"");
           studentRepository.save(s3);
        });
    }

    @Test
    public void delete(){
        assertEquals("Marius", studentRepository.delete("1").getPrenume());
        assertThrows(IllegalArgumentException.class, ()->{
           studentRepository.save(null);
        });
        int size = 0;
        for (Student s : studentRepository.findAll()){
            size++;
        }
        assertEquals(0, size);
    }

    @Test
    public void update(){}
}
