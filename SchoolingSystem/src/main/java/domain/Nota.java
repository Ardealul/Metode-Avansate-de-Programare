package domain;

import com.sun.tools.javac.util.Pair;

import java.time.LocalDate;
import java.util.Objects;

public class Nota extends Entity<Pair<String, String>> {
    private LocalDate data;
    private String profesor;
    private Float nota;

    /*private Student student;
    private Tema tema;*/

    public Nota(Pair<String, String> id, LocalDate data, String profesor, Float nota) {
        super(id);
        this.data = data;
        this.profesor = profesor;
        this.nota = nota;

        /*this.student = student;
        this.tema = tema;*/
    }

    public String getStudent() {
        return super.getId().fst;
    }

    public String getTema() {
        return super.getId().snd;
    }


    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota1 = (Nota) o;
        return Objects.equals(data, nota1.data) &&
                Objects.equals(profesor, nota1.profesor) &&
                Objects.equals(nota, nota1.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, profesor, nota);
    }

    @Override
    public String toString() {
        return getId().fst + "; " +
                getId().snd + "; " +
                data + "; " +
                profesor + "; " +
                nota + "; ";
    }
}
