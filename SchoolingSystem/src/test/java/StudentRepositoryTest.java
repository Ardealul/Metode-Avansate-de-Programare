import domain.Student;
import domain.validators.StudentValidator;
import domain.validators.ValidationException;
import domain.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.RepositoryException;
import repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class StudentRepositoryTest {
    Student s;
    StudentRepository studentRepository;

    @BeforeEach
    public void setUp() throws Exception{
        Validator<Student> validatorStudent = new StudentValidator();
        studentRepository = new StudentRepository(validatorStudent);
        s = new Student("1", "Aldea", "Vipera", "vip@gmail.com", 223, "Vescan Andreea");
        studentRepository.save(s);
    }

    @Test
    public void update(){
        assertEquals(s, studentRepository.update(s));
        assertThrows(IllegalArgumentException.class, ()->{
            studentRepository.update(null);
        });
        assertThrows(RepositoryException.class, ()->{
            Student s2 = new Student("100", "A","B","C", 221,"D");
            studentRepository.update(s2);
        });
        assertThrows(ValidationException.class, ()->{
           Student s3 = new Student("","","","", -1, "");
           studentRepository.update(s3);
        });
    }
}
