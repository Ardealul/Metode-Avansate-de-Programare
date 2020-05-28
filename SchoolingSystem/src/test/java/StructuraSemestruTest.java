import domain.StructuraSemestru;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructuraSemestruTest {
    StructuraSemestru sem = new StructuraSemestru("1",
            2,
            1,
            LocalDate.of(2019, 9, 30),
            LocalDate.of(2019, 12, 23),
            LocalDate.of(2020, 1, 5),
            LocalDate.of(2020, 1, 19));

    @Test
    public void getAnUniversitar(){
        assertEquals(2, sem.getAnUniversitar());
    }

    @Test
    public void setAnUniversitar(){
        sem.setAnUniversitar(3);
        assertEquals(3, sem.getAnUniversitar());
    }

    @Test
    public void getSemestru(){
        assertEquals(1, sem.getSemestru());
    }

    @Test
    public void setSemestru(){
        sem.setSemestru(2);
        assertEquals(2, sem.getSemestru());
    }

    @Test
    public void getInceputSem(){
        assertEquals(LocalDate.of(2019, 9,30), sem.getInceputSem());
    }

    @Test
    public void setInceputSem(){
        sem.setInceputSem(LocalDate.of(2019,10,1));
        assertEquals(LocalDate.of(2019,10,1), sem.getInceputSem());
    }

    @Test
    public void getInceputVac(){
        assertEquals(LocalDate.of(2019, 12,23), sem.getInceputVac());
    }

    @Test
    public void setInceputVac(){
        sem.setInceputVac(LocalDate.of(2019, 12, 22));
        assertEquals(LocalDate.of(2019, 12, 22), sem.getInceputVac());
    }

    @Test
    public void getSfarsitVac(){
        assertEquals(LocalDate.of(2020, 1, 5), sem.getSfarsitVac());
    }

    @Test
    public void setSfarsitVac(){
        sem.setSfarsitVac(LocalDate.of(2020, 1, 6));
        assertEquals(LocalDate.of(2020, 1, 6), sem.getSfarsitVac());
    }

    @Test
    public void getSfarsitSem(){
        assertEquals(LocalDate.of(2020, 1, 19), sem.getSfarsitSem());
    }

    @Test
    public void setSfarsitSem(){
        sem.setSfarsitSem(LocalDate.of(2020, 1, 18));
        assertEquals(LocalDate.of(2020, 1, 18), sem.getSfarsitSem());
    }

}
