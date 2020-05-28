package domain;

import java.time.LocalDate;
import java.util.Calendar;

public class StructuraAnUniversitar extends Entity<String> {
    private static StructuraAnUniversitar instance = null;
    private Integer anUniversitar;
    private StructuraSemestru sem1;
    private StructuraSemestru sem2;

    public StructuraAnUniversitar(String id, Integer anUniversitar, Integer sem1, LocalDate inceputSem1, LocalDate inceputVac1, LocalDate sfarsitVac1, LocalDate sfarsitSem1, Integer sem2, LocalDate inceputSem2, LocalDate inceputVac2, LocalDate sfarsitVac2, LocalDate sfarsitSem2) {
        super(id);
        this.anUniversitar = anUniversitar;
        this.sem1 = new StructuraSemestru("1", anUniversitar, sem1, inceputSem1, inceputVac1, sfarsitVac1, sfarsitSem1);
        this.sem2 = new StructuraSemestru("2", anUniversitar, sem2, inceputSem2, inceputVac2, sfarsitVac2, sfarsitSem2);
    }

    //singleton
    public static StructuraAnUniversitar getInstance(String id, Integer anUniversitar, Integer sem1, LocalDate inceputSem1, LocalDate inceputVac1, LocalDate sfarsitVac1, LocalDate sfarsitSem1, Integer sem2, LocalDate inceputSem2, LocalDate inceputVac2, LocalDate sfarsitVac2, LocalDate sfarsitSem2){
        if(instance == null){
            instance = new StructuraAnUniversitar(id, anUniversitar, sem1, inceputSem1, inceputVac1, sfarsitVac1, sfarsitSem1, sem2, inceputSem2, inceputVac2, sfarsitVac2, sfarsitSem2);
        }
        return instance;
    }

    public static StructuraAnUniversitar getInstance(){ return instance; }

    public Integer getAnUniversitar() {
        return anUniversitar;
    }

    public void setAnUniversitar(Integer anUniversitar) {
        this.anUniversitar = anUniversitar;
    }

    public StructuraSemestru getSem1() {
        return sem1;
    }

    public void setSem1(StructuraSemestru sem1) {
        this.sem1 = sem1;
    }

    public StructuraSemestru getSem2() {
        return sem2;
    }

    public void setSem2(StructuraSemestru sem2) {
        this.sem2 = sem2;
    }

    public Integer getWeek(LocalDate date, StructuraSemestru sem){
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        //sapt curenta
        Integer currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        ////System.out.println("Sapt curenta: " + currentWeek);

        calendar.set(sem.getInceputSem().getYear(),sem.getInceputSem().getMonthValue() - 1, sem.getInceputSem().getDayOfMonth());

        //sapt inceputului de semestru
        Integer inceputSemWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        //System.out.println("Sapt inceputului sem: " + inceputSemWeek);

        calendar.set(sem.getInceputVac().getYear(), sem.getInceputVac().getMonthValue() - 1, sem.getInceputVac().getDayOfMonth());

        //sapt inceputului de vacanta
        Integer inceputVacWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        //System.out.println("Sapt inceputului vac: " + inceputVacWeek);

        calendar.set(sem.getSfarsitVac().getYear(), sem.getSfarsitVac().getMonthValue() - 1, sem.getSfarsitVac().getDayOfMonth());

        //sapt sfarsitului de vacanta
        Integer sfarsitVacWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        //System.out.println("Sapt sfarsitului vac: " + sfarsitVacWeek);

        if (sem.getInceputSem().isBefore(date) && sem.getInceputVac().isAfter(date))
            return currentWeek - inceputSemWeek + 1;
        if (sem.getSfarsitSem().isAfter(date) && sem.getSfarsitVac().isBefore(date))
            return (currentWeek - sfarsitVacWeek + 1) + (inceputVacWeek - inceputSemWeek + 1);

        ////System.out.println("adevarata sapt curenta " + currentWeek);
        return currentWeek;
    }
}
