package domain;

public class StudentMedie extends Entity<String> {
    String nume;
    Integer grupa;
    Float medie;

    public StudentMedie(String id, String nume, Integer grupa, Float medie) {
        super(id);
        this.nume = nume;
        this.grupa = grupa;
        this.medie = medie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public Float getMedie() {
        return medie;
    }

    public void setMedie(Float medie) {
        this.medie = medie;
    }
}
