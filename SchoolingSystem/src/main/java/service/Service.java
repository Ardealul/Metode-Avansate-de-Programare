package service;

import com.sun.tools.javac.util.Pair;
import domain.*;
import events.ChangeEvent;
import events.ChangeEventType;
import observer.Observable;
import observer.Observer;
import repository.CrudRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Observable<ChangeEvent> {
    private static Integer currentWeek;
    private CrudRepository<String, Student> studentRepository;
    private CrudRepository<String, Tema> temaRepository;
    private CrudRepository<Pair<String, String>, Nota> notaRepository;
    StructuraAnUniversitar structuraAnUniversitar = StructuraAnUniversitar.getInstance(
            "1",
            1,
            1,
            LocalDate.of(2019, 9, 30),
            LocalDate.of(2019, 12, 23),
            LocalDate.of(2020, 1, 5),
            LocalDate.of(2020, 1, 17),
            2,
            LocalDate.of(2020, 2, 24),
            LocalDate.of(2020, 4, 17),
            LocalDate.of(2020, 4, 27),
            LocalDate.of(2020, 6, 5));

    public Service(CrudRepository<String, Student> studentFileRepository, CrudRepository<String, Tema> temaFileRepository, CrudRepository<Pair<String, String>, Nota> notaFileRepository) {
        this.studentRepository = studentFileRepository;
        this.temaRepository = temaFileRepository;
        this.notaRepository = notaFileRepository;
    }

    public static void setCurrentWeek(Integer currentWeek) {
        Service.currentWeek = currentWeek;
    }

    //student
    public Student findOneStudent(String id) {
        return studentRepository.findOne(id);
    }

    public Iterable<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    public Student saveStudent(String id, String nume, String prenume, String email, Integer grupa, String cadruDidacticIndrumatorLab) {
        Student s = new Student(id, nume, prenume, email, grupa, cadruDidacticIndrumatorLab);
        Student s1 = studentRepository.save(s);
        if (s1 == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, s1));
        }
        return s1;
    }

    public Student deleteStudent(String id) {
        Student s = studentRepository.delete(id);
        if (s != null) {
            notifyObservers(new ChangeEvent(ChangeEventType.DELETE, s));
        }
        return s;
    }

    public Student updateStudent(String id, String nume, String prenume, String email, Integer grupa, String cadruDidacticIndurmatorLab) {
        Student s = new Student(id, nume, prenume, email, grupa, cadruDidacticIndurmatorLab);
        Student oldStudent = studentRepository.update(s);
        if (oldStudent == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, s, oldStudent));
            return oldStudent;
        }
        return oldStudent;
    }

    public List<Student> getAllStudent() {
        List<Student> all = new ArrayList<>();
        studentRepository.findAll().forEach(all::add);
        return all;
    }

    //tema
    public Tema findOneTema(String id) {
        return temaRepository.findOne(id);
    }

    public Iterable<Tema> findAllTema() {
        return temaRepository.findAll();
    }

    public Tema saveTema(String id, String descriere, Integer startWeek, Integer deadlineWeek) {
        Tema t = new Tema(id, descriere, startWeek, deadlineWeek);
        Tema t1 = temaRepository.save(t);
        if (t1 == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, t1));
        }
        return t1;
    }

    public Tema deleteTema(String id) {
        Tema t = temaRepository.delete(id);
        if (t != null) {
            notifyObservers(new ChangeEvent(ChangeEventType.DELETE, t));
        }
        return t;
    }

    public Tema updateTema(String id, String descriere, Integer startWeek, Integer deadlineWeek) {
        Tema t = new Tema(id, descriere, startWeek, deadlineWeek);
        Tema oldTema = temaRepository.update(t);
        if (oldTema == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, t, oldTema));
            return oldTema;
        }
        return oldTema;
    }

    public List<Tema> getAllTema() {
        List<Tema> all = new ArrayList<>();
        temaRepository.findAll().forEach(all::add);
        return all;
    }

    //nota
    public Nota findOneNota(Pair<String, String> id) {
        return notaRepository.findOne(id);
    }

    public Iterable<Nota> findAllNota() {
        return notaRepository.findAll();
    }

    public Nota saveNota(String idStudent, String idTema, LocalDate data, String profesor, Float valoare, float motivare, float intarziere, String feedback) {
        Student student = studentRepository.findOne(idStudent);
        Tema tema = temaRepository.findOne(idTema);
        if (student == null || tema == null) {
            throw new ServiceException("Nota nu se poate asigna, id invalid!");
        }
        Integer saptPredare = StructuraAnUniversitar.getInstance().getWeek(data, StructuraAnUniversitar.getInstance().getSem1());

        float intarziereStudent = saptPredare - tema.getDeadlineWeek();
        float result = intarziereStudent - intarziere - motivare;
        float valoareFinala = valoare;
        if (intarziereStudent <= 0)
            System.out.println("Nu a existat intarziere.");
        else
            System.out.println("Initial intarzierea a fost de " + (int) intarziereStudent + " sapt.");
        System.out.println("Studentul a avut motivare " + (int) motivare + " sapt.");
        System.out.println("Profesorul a intarziat introducerea notei cu " + (int) intarziere + " sapt.");

        if (result == 1 || result == 2) {
            valoareFinala = valoare - result;
            System.out.println("In concluzie, a intarziat predarea temei " + (int) result + " sapt, astfel nota finala nu va fi " + valoare + ", ci " + valoareFinala);
        } else if (result >= 3) {
            System.out.println("In concluzie, a intarziat cu mai mult de 2 sapt predarea temei, astfel nota finala va fi 1.");
            valoareFinala = 1f;
        } else {
            System.out.println("In concluzie, nota finala va fi " + valoareFinala);
        }
        Nota nota = new Nota(new Pair<>(idStudent, idTema), data, profesor, valoareFinala);
        //
        saveToTextFile(nota, student.getNume(), feedback);
        //
        Nota n = notaRepository.save(nota);
        if (n == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, n));
        }
        return n;
    }

    public Float getNotaFinala(String idStudent, String idTema, LocalDate data, Float valoare, float motivare, float intarziere) {
        Student student = studentRepository.findOne(idStudent);
        Tema tema = temaRepository.findOne(idTema);
        if (student == null || tema == null) {
            throw new ServiceException("Nota nu se poate asigna, id invalid!");
        }
        Integer saptPredare = StructuraAnUniversitar.getInstance().getWeek(data, StructuraAnUniversitar.getInstance().getSem1());

        float intarziereStudent = saptPredare - tema.getDeadlineWeek();
        float result = intarziereStudent - intarziere - motivare;
        float valoareFinala = valoare;

        if (result == 1 || result == 2) {
            valoareFinala = valoare - result;
        } else if (result >= 3) {
            valoareFinala = 1f;
        }
        return valoareFinala;
    }

    public Nota deleteNota(Pair<String, String> id) {
        Nota n = notaRepository.delete(id);
        if (n != null) {
            notifyObservers(new ChangeEvent(ChangeEventType.DELETE, n));
        }
        return n;
    }

    public Nota updateNota(String idStudent, String idTema, LocalDate data, String profesor, Float valoare) {
        Nota nota = new Nota(new Pair<>(idStudent, idTema), data, profesor, valoare);
        Nota n = notaRepository.update(nota);
        if (n == null) {
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, nota, n));
            return n;
        }
        return n;
    }

    public List<Nota> getAllNota() {
        List<Nota> all = new ArrayList<>();
        notaRepository.findAll().forEach(all::add);
        return all;
    }

    public void saveToTextFile(Nota nota, String fileName, String feedback) {
        try (FileWriter writer = new FileWriter("data/" + fileName + ".txt", true)) {
            writer.write("Tema: " + nota.getId().snd);
            writer.write(System.getProperty("line.separator"));
            writer.write("Nota: " + nota.getNota());
            writer.write(System.getProperty("line.separator"));
            writer.write("Predata in saptamana: " + StructuraAnUniversitar.getInstance().getWeek(nota.getData(), StructuraAnUniversitar.getInstance().getSem1()));
            writer.write(System.getProperty("line.separator"));
            Tema t = findOneTema(nota.getId().snd);
            writer.write("Deadline: " + t.getDeadlineWeek());
            writer.write(System.getProperty("line.separator"));
            writer.write("Feedback: " + feedback);
            writer.write(System.getProperty("line.separator"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //filtrare 1: toti studentii unei grupe
    public Iterable<Student> getStudentByGrupa(Integer grupa) {
        return StreamSupport.stream(this.studentRepository.findAll().spliterator(), false)
                .filter(x -> x.getGrupa().equals(grupa))
                .collect(Collectors.toList());
    }

    //obtine lista studentilor dintr-o lista de note
    public Iterable<Student> getStudent(Iterable<Nota> lista) {
        List<Student> rezultat = new ArrayList<>();
        lista.forEach(nota -> {
            Student student = studentRepository.findOne(nota.getId().fst);
            rezultat.add(student);
        });
        return rezultat;
    }

    //filtrare 2: toti studentii care au predat o anumita tema
    public Iterable<Nota> getStudentByTema(String id) {
        return StreamSupport.stream(this.notaRepository.findAll().spliterator(), false)
                .filter(x -> x.getId().snd.equals(id))
                .collect(Collectors.toList());
    }

    //filtrare 3: toti studentii care au predat o anumita tema unui anumit profesor
    public Iterable<Nota> getStudentByTemaAndProfesor(String idTema, String profesor) {
        return StreamSupport.stream(this.notaRepository.findAll().spliterator(), false)
                .filter(x -> x.getId().snd.equals(idTema))
                .filter(x -> x.getProfesor().equals(profesor))
                .collect(Collectors.toList());
    }

    //filtrare 4: toate notele la o anumita tema dintr-o sapt data
    public Iterable<Nota> getNotaByTemaAndData(String idTema, Integer data) {
        return StreamSupport.stream(this.notaRepository.findAll().spliterator(), false)
                .filter(x -> x.getId().snd.equals(idTema) && data.equals(StructuraAnUniversitar.getInstance().getWeek(x.getData(), StructuraAnUniversitar.getInstance().getSem1())))
                .collect(Collectors.toList());
    }

    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    //media ponderata a studentilor
    public List<StudentMedie> getAllStudentiMedie() {
        List<StudentMedie> list = new ArrayList<>();
        Integer sumaPonderi = 0;
        for (Tema tema : getAllTema()) {
            sumaPonderi += (tema.getDeadlineWeek() - tema.getStartWeek());
        }
        for (Student student : getAllStudent()) {
            Float suma = 0f;
            Float medieFinala = 0f;
            Integer pondere;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    Tema tema = findOneTema(nota.getId().snd);
                    pondere = tema.getDeadlineWeek() - tema.getStartWeek();
                    suma += nota.getNota() * pondere;
                }
            }
            if (suma != 0)
                medieFinala = suma / sumaPonderi;
            else
                medieFinala = 0f;
            medieFinala = round(medieFinala, 2);
            StudentMedie studentMedie = new StudentMedie(student.getId(), student.getNume(), student.getGrupa(), medieFinala);
            list.add(studentMedie);
        }
        return list;
    }

    //studentii care pot intra in examen
    public List<StudentMedie> getAllStudentiExamen() {
        List<StudentMedie> list = new ArrayList<>();
        Integer sumaPonderi = 0;
        for (Tema tema : getAllTema()) {
            sumaPonderi += (tema.getDeadlineWeek() - tema.getStartWeek());
        }
        for (Student student : getAllStudent()) {
            Float suma = 0f;
            Float medieFinala = 0f;
            Integer pondere;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    Tema tema = findOneTema(nota.getId().snd);
                    pondere = tema.getDeadlineWeek() - tema.getStartWeek();
                    suma += nota.getNota() * pondere;
                }
            }
            if (suma != 0)
                medieFinala = suma / sumaPonderi;
            else
                medieFinala = 0f;
            medieFinala = round(medieFinala, 2);
            StudentMedie studentMedie = new StudentMedie(student.getId(), student.getNume(), student.getGrupa(), medieFinala);
            if (studentMedie.getMedie() >= 4) {
                list.add(studentMedie);
            }
        }
        return list.stream()
                .sorted((p1, p2) -> p2.getMedie().compareTo(p1.getMedie())).collect(Collectors.toList());
    }

    //cele mai grele teme
    public List<TemaMedie> getAllTemeMedie() {
        List<TemaMedie> list = new ArrayList<>();
        for (Tema tema : getAllTema()) {
            Float suma = 0f;
            Float media = 0f;
            Integer nr = 0;
            for (Nota nota : getAllNota()) {
                if (nota.getId().snd.equals(tema.getId())) {
                    suma += nota.getNota();
                    nr += 1;
                }
            }
            if (suma != 0)
                media = suma / nr;
            else
                media = 0f;
            media = round(media, 2);
            TemaMedie temaMedie = new TemaMedie(tema.getId(), tema.getDescriere(), media);
            list.add(temaMedie);
        }
        return list.stream()
                .sorted(Comparator.comparing(TemaMedie::getMedie)).collect(Collectors.toList());
    }

    //studentii care au predat toate temele la timp
    public List<Student> getAllStudentiSilitori() {
        List<Student> list = new ArrayList<>();
        for (Student student : getAllStudent()) {
            boolean laTimp = true;
            boolean macarUna = false;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    macarUna = true;
                    Tema tema = findOneTema(nota.getId().snd);
                    if (tema.getDeadlineWeek() < StructuraAnUniversitar.getInstance().getWeek(nota.getData(), StructuraAnUniversitar.getInstance().getSem1()))
                        laTimp = false;
                }
            }
            if (laTimp && macarUna)
                list.add(student);
        }
        return list;
    }


    private List<Observer<ChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
