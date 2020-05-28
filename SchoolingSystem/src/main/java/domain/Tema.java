package domain;

import java.util.Objects;

public class Tema extends Entity<String> {
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Tema(String id, String descriere, Integer startWeek, Integer deadlineWeek) {
        super(id);
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(Integer deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    @Override
    public String toString() {
        return getId() +
                "; " + descriere +
                "; " + startWeek +
                "; " + deadlineWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tema tema = (Tema) o;
        return Objects.equals(descriere, tema.descriere) &&
                Objects.equals(startWeek, tema.startWeek) &&
                Objects.equals(deadlineWeek, tema.deadlineWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), descriere, startWeek, deadlineWeek);
    }

}
