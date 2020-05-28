import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotaTest {
    Student st;
    Tema t;
    Nota n;

    @BeforeEach
    void setUp() {
        st = new Student("1", "Cailean", "Marius", "cm", 221,"Vescan");
        t = new Tema("1", "lab1", 7,8);
        n = new Nota(new Pair<String, String>("1", "1"), LocalDate.of(2019,12,12), "Vescan", 9f);
    }

    @Test
    void getData() {
        assertEquals(LocalDate.of(2019,12,12), n.getData());
    }

    @Test
    void setData() {
        n.setData(LocalDate.of(2019,12,13));
        assertEquals(LocalDate.of(2019,12,13), n.getData());
    }

    @Test
    void getProfesor() {
        assertEquals("Vescan", n.getProfesor());
    }

    @Test
    void setProfesor() {
        n.setProfesor("Camelia");
        assertEquals("Camelia", n.getProfesor());
    }

    @Test
    void getNota() {
        assertEquals(9f, n.getNota());
    }

    @Test
    void setNota() {
        n.setNota(9.5f);
        assertEquals(9.5f, n.getNota());
    }
}