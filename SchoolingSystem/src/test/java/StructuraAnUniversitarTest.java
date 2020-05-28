import domain.StructuraAnUniversitar;
import domain.StructuraSemestru;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StructuraAnUniversitarTest {
    StructuraAnUniversitar structuraAnUniversitar = StructuraAnUniversitar.getInstance("1",
        1,
        1, LocalDate.of(2019, 9, 30),
        LocalDate.of(2019,12,23),
        LocalDate.of(2020,1,5),
        LocalDate.of(2020,1,17),
        2, LocalDate.of(2020, 1, 24),
        LocalDate.of(2020, 4, 22),
        LocalDate.of(2020,4,30),
        LocalDate.of(2020, 6, 30));

    @Test
    public void getAnUniversitar(){
        assertEquals(2, structuraAnUniversitar.getAnUniversitar());
    }

    @Test
    public void setStructuraAnUniversitar(){
        structuraAnUniversitar.setAnUniversitar(2);
        assertEquals(2, structuraAnUniversitar.getAnUniversitar());
    }

    @Test
    public void getSem1(){
        assertNotEquals(null, structuraAnUniversitar.getSem1());
    }

    @Test
    public void getSem2(){
        assertNotEquals(null, structuraAnUniversitar.getSem2());
    }

    @Test
    public void setSem1(){
        StructuraSemestru sem1 = new StructuraSemestru("1",
                2,1,
                LocalDate.of(2020, 10, 1),
                LocalDate.of(2020, 12, 23),
                LocalDate.of(2021, 1, 7),
                LocalDate.of(2021, 1, 21));
        structuraAnUniversitar.setSem1(sem1);
        assertEquals(sem1, structuraAnUniversitar.getSem1());
    }

    @Test
    public void setSem2(){
        StructuraSemestru sem2 = new StructuraSemestru("2",
                2, 1,
                LocalDate.of(2020, 10, 1),
                LocalDate.of(2020, 12, 23),
                LocalDate.of(2021, 1, 7),
                LocalDate.of(2021, 1, 21));
        structuraAnUniversitar.setSem2(sem2);
        assertEquals(sem2, structuraAnUniversitar.getSem2());
    }

    @Test
    public void getWeek(){
        assertEquals(StructuraAnUniversitar.getInstance().getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1()),
                StructuraAnUniversitar.getInstance().getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1()));
    }
}
