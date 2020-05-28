package domain;

public class TemaMedie extends Entity<String> {
    String descriere;
    Float medie;

    public TemaMedie(String id, String descriere, Float medie) {
        super(id);
        this.descriere = descriere;
        this.medie = medie;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Float getMedie() {
        return medie;
    }

    public void setMedie(Float medie) {
        this.medie = medie;
    }
}
