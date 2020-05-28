import domain.Student;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

//import org.junit.Before;

public class StudentTest {

    Student s = new Student("1", "Cailean" ,"Marius", "imiplaceanaliza@gmail.com", 221 ,"Vescan Andreea");

    @Test
    public void getNume() {
        assertEquals("Cailean", s.getNume());
    }

    @Test
    public void setNume() {
        s.setNume("Cadis");
        assertEquals("Cadis", s.getNume());
    }

    @Test
    public void getPrenume() {
        assertEquals("Marius", s.getPrenume());
    }

    @Test
    public void setPrenume() {
        s.setPrenume("Poli");
        assertEquals("Poli", s.getPrenume());
    }

    @Test
    public void getEmail() {
        assertEquals("imiplaceanaliza@gmail.com", s.getEmail());
    }

    @Test
    public void setEmail() {
        s.setEmail("cadispoli@gmail.com");
        assertEquals("cadispoli@gmail.com", s.getEmail());
    }

    @Test
    public void getGrupa() {
        assertEquals(Integer.valueOf(221), s.getGrupa());
    }

    @Test
    public void setGrupa() {
        s.setGrupa(222);
        assertEquals(Integer.valueOf(222), s.getGrupa());
    }

    @Test
    public void getCadruDidacticIndrumatorLab() {
        assertEquals("Vescan Andreea", s.getCadruDidacticIndrumatorLab());
    }

    @Test
    public void setCadruDidacticIndrumatorLab() {
        s.setCadruDidacticIndrumatorLab("Serban Camelia");
        assertEquals("Serban Camelia", s.getCadruDidacticIndrumatorLab());

    }
}