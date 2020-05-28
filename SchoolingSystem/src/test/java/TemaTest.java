import domain.Tema;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TemaTest {

    Tema t = new Tema("1", "laborator1", 1, 2);

    @Test
    public void getDescriere() {
        assertEquals("laborator1", t.getDescriere());
    }

    @Test
    public void setDescriere() {
        t.setDescriere("laborator2");
        assertEquals("laborator2", t.getDescriere());
    }

    @Test
    public void getStartWeek() {
        assertEquals(Integer.valueOf(1), t.getStartWeek());
    }

    @Test
    public void setStartWeek() {
        t.setStartWeek(2);
        assertEquals(Integer.valueOf(2), t.getStartWeek());
    }

    @Test
    public void getDeadlineWeek() {
        assertEquals(Integer.valueOf(2), t.getDeadlineWeek());
    }

    @Test
    public void setDeadlineWeek() {
        t.setDeadlineWeek(3);
        assertEquals(Integer.valueOf(3), t.getDeadlineWeek());
    }
}