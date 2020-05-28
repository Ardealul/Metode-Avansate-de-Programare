package domain;

import java.time.LocalDate;

public class StructuraSemestru extends Entity<String> {
    Integer anUniversitar;
    Integer semestru;
    private LocalDate inceputSem;
    private LocalDate inceputVac;
    private LocalDate sfarsitVac;
    private LocalDate sfarsitSem;

    public StructuraSemestru(String id, Integer anUniversitar, Integer semestru, LocalDate inceputSem, LocalDate inceputVac, LocalDate sfarsitVac, LocalDate sfarsitSem) {
        super(id);
        this.anUniversitar = anUniversitar;
        this.semestru = semestru;
        this.inceputSem = inceputSem;
        this.inceputVac = inceputVac;
        this.sfarsitVac = sfarsitVac;
        this.sfarsitSem = sfarsitSem;
    }

    public LocalDate getInceputSem() {
        return inceputSem;
    }

    public void setInceputSem(LocalDate inceputSem) {
        this.inceputSem = inceputSem;
    }

    public LocalDate getInceputVac() {
        return inceputVac;
    }

    public void setInceputVac(LocalDate inceputVac) {
        this.inceputVac = inceputVac;
    }

    public LocalDate getSfarsitVac() {
        return sfarsitVac;
    }

    public void setSfarsitVac(LocalDate sfarsitVac) {
        this.sfarsitVac = sfarsitVac;
    }

    public LocalDate getSfarsitSem() {
        return sfarsitSem;
    }

    public void setSfarsitSem(LocalDate sfarsitSem) {
        this.sfarsitSem = sfarsitSem;
    }

    public Integer getSemestru() {
        return semestru;
    }

    public void setSemestru(Integer semestru) {
        this.semestru = semestru;
    }

    public Integer getAnUniversitar() {
        return anUniversitar;
    }

    public void setAnUniversitar(Integer anUniversitar) {
        this.anUniversitar = anUniversitar;
    }
}
