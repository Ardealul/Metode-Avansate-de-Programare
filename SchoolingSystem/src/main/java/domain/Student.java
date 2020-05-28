package domain;

import java.util.Objects;

public class Student extends Entity<String> {
    private String nume;
    private String prenume;
    private String email;
    private Integer grupa;
    private String cadruDidacticIndrumatorLab;

    public Student(String id, String nume, String prenume, String email, Integer grupa, String cadruDidacticIndrumatorLab) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.grupa = grupa;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    @Override
    public String toString() {
        return getId() +
                "; " + nume +
                "; " + prenume +
                "; " + email +
                "; " + grupa +
                "; " + cadruDidacticIndrumatorLab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(nume, student.nume) &&
                Objects.equals(prenume, student.prenume) &&
                Objects.equals(email, student.email) &&
                Objects.equals(grupa, student.grupa) &&
                Objects.equals(cadruDidacticIndrumatorLab, student.cadruDidacticIndrumatorLab);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), prenume, email, grupa, cadruDidacticIndrumatorLab);
    }
}
