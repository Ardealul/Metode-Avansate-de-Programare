package ui;

import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validators.ValidationException;
import repository.RepositoryException;
import service.Service;
import service.ServiceException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Consola {
    private static Integer currentWeek;
    private MeniuComenda mainMeniu;
    private Service service;

    public Consola(Service service) {
        this.service = service;
        Service.setCurrentWeek(currentWeek);
    }

    public static void setCurrentWeek(Integer currentWeek){ Consola.currentWeek = currentWeek; }

    public void createMeniu() {
        System.out.println("Saptamana curenta este: " + currentWeek);
        mainMeniu = new MeniuComenda("MENIUL PRINCIPAL:");
        MeniuComenda crudStudent = new MeniuComenda("CRUD Student: ");
        crudStudent.addComanda("1. Adauga student", new AddStudentComanda());
        crudStudent.addComanda("2. Sterge student", new DeleteStudentComanda());
        crudStudent.addComanda("3. Gaseste student", new FindStudentComanda());
        crudStudent.addComanda("4. Modifica student", new UpdateStudentComanda());
        crudStudent.addComanda("5. Inapoi la MENIUL PRINCIPAL", mainMeniu);

        MeniuComenda crudTema = new MeniuComenda("CRUD Tema: ");
        crudTema.addComanda("1. Adauga tema", new AddTemaComanda());
        crudTema.addComanda("2. Sterge tema", new DeleteTemaComanda());
        crudTema.addComanda("3. Gaseste tema", new FindTemaComanda());
        crudTema.addComanda("4. Modifica tema", new UpdateTemaComanda());
        crudTema.addComanda("5. Inapoi in MENIUL PRINCIPAL", mainMeniu);

        MeniuComenda crudNota = new MeniuComenda("CRUD Nota");
        crudNota.addComanda("1. Adauga nota", new AddNotaComanda());
        crudNota.addComanda("2. Sterge nota", new DeleteNotaComanda());
        crudNota.addComanda("3. Gaseste nota", new FindNotaComanda());
        crudNota.addComanda("4. Modifica nota", new UpdateNotaComanda());
        crudNota.addComanda("5. --> MeniuPrincipal", mainMeniu);

        MeniuComenda filtrari = new MeniuComenda("Filtrari");
        filtrari.addComanda("1. Toti studentii dintr-o anumita grupa", new FiltrareTotiStudentiiUneiGrupe());
        filtrari.addComanda("2. Toti studentii care au predat o anumita tema" ,new FiltrareTotiStudentiiCareAuPredatOAnumitaTema());
        filtrari.addComanda("3. Toti studentii care au predat o anumita tema unui anumit profesor", new FiltrareTotiStudentiiCareAuPredatOAnumitaTemaUnuiAnumitProfesor());
        filtrari.addComanda("4. Toate notele la o anumita tema dintr-o anumita saptamana", new FiltrareToateNoteleLaOAnumitaTemaDintrOAnumitaSaptamana());
        filtrari.addComanda("5. --> MeniuPrincipal", mainMeniu);

        mainMeniu.addComanda("1. CRUD Student", crudStudent);
        mainMeniu.addComanda("2. CRUD Tema", crudTema);
        mainMeniu.addComanda("3. CRUD Nota", crudNota);
        mainMeniu.addComanda("4. Filtrari", filtrari);
        mainMeniu.addComanda("5. Exit", new ExitComanda());
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        createMeniu();
        MeniuComenda currentMeniu = mainMeniu;
        try {
            while (true) {
                System.out.println("_______________________");
                System.out.println(currentMeniu.getNameMeniu());
                System.out.println("_______________________");
                currentMeniu.execute();
                System.out.println("Introdu o comanda: ");

                Integer nrComanda = scanner.nextInt();
                if (nrComanda > 0 && nrComanda <= currentMeniu.getComenzi().size()) {
                    Comanda selectedComanda = currentMeniu.getComenzi().get(nrComanda - 1);
                    if (selectedComanda instanceof MeniuComenda)
                        currentMeniu = (MeniuComenda) selectedComanda;
                    else
                        selectedComanda.execute();
                } else
                    System.out.println("Comanda invalida! Reincercati cu mai multa atentie!");
            }
            } catch (InputMismatchException e){ System.out.println("Introdu datele cu mai multa atentie!"); }
    }

    private class AddStudentComanda implements Comanda {
        @Override
        public void execute() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID: ");
                String id = scanner.nextLine();
                System.out.println("Nume: ");
                String nume = scanner.nextLine();
                System.out.println("Prenume: ");
                String prenume = scanner.nextLine();
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Cadru didactic: ");
                String cadruDidacticIndrumatorLab = scanner.nextLine();
                System.out.println("Grupa: ");
                Integer grupa = scanner.nextInt();
                scanner.nextLine();
                Student st = service.saveStudent(id, nume, prenume, email, grupa, cadruDidacticIndrumatorLab);
                if(st == null){
                    System.out.println("Studentul cu id-ul " + id + " a fost salvat!");
                }
                else
                    System.out.println("Studentul cu id-ul " + st.getId() + " deja salvat!");

            } catch (IllegalArgumentException | ValidationException | RepositoryException e) {
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                System.out.println("Grupa trebuie sa fie int!");
            }
        }
    }

    private class DeleteStudentComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID: ");
            String id = scanner.nextLine();
            try {
                Student st = service.deleteStudent(id);
                if(st == null){
                    System.out.println("Nu exista studentul cu id-ul " + id + "!");
                }
                else
                    System.out.println("Studentul cu id-ul " + st.getId() + " a fost sters!");
            } catch (IllegalArgumentException | RepositoryException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private class FindStudentComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID: ");
            String id = scanner.nextLine();
            try {
                Student s = service.findOneStudent(id);
                System.out.println("Rezultatul gasit este: " + s.toString());
            } catch (IllegalArgumentException | RepositoryException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private class UpdateStudentComanda implements Comanda {
        @Override
        public void execute() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID: ");
                String id = scanner.nextLine();
                System.out.println("Nume: ");
                String nume = scanner.nextLine();
                System.out.println("Prenume: ");
                String prenume = scanner.nextLine();
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Cadru didactic: ");
                String cadruDidacticIndrumatorLab = scanner.nextLine();
                System.out.println("Grupa: ");
                Integer grupa = scanner.nextInt();
                scanner.nextLine();
                Student st = service.updateStudent(id, nume, prenume, email, grupa, cadruDidacticIndrumatorLab);
                if(st == null){
                    System.out.println("Studentul cu id-ul " + id + " a fost updatat!");
                }
                else
                    System.out.println("Nu exista studentul cu id-ul " + id + " !");
                System.out.println("Studentul cu id-ul " + id + " updatat!");
            } catch (IllegalArgumentException | ValidationException | RepositoryException e) {
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                System.out.println("Grupa trebuie sa fie un int!");
            }
        }
    }

    private class AddTemaComanda implements Comanda {
        @Override
        public void execute() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID: ");
                String id = scanner.nextLine();
                System.out.println("Descriere: ");
                String descriere = scanner.nextLine();
                System.out.println("DeadlineWeek: ");
                Integer deadlineWeek = scanner.nextInt();
                scanner.nextLine();
                Tema t = service.saveTema(id, descriere, currentWeek, deadlineWeek);
                if(t == null){
                    System.out.println("Tema cu id-ul " + id + " a fost salvata!");
                }
                else
                    System.out.println("Tema cu id-ul " + t.getId() + " deja salvata!");
            }
            catch(IllegalArgumentException | ValidationException | RepositoryException e){
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                System.out.println("DeadlineWeek-ul trebuie sa fie int!");
            }
        }
    }

    private class DeleteTemaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID:");
            String id = scanner.nextLine();
            try{
                Tema t = service.deleteTema(id);
                if(t == null){
                    System.out.println("Nu exista tema cu id-ul " + id + "!");
                }
                else
                    System.out.println("Tema cu id-ul " + t.getId() + " a fost stearsa!");
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FindTemaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID: ");
            String id = scanner.nextLine();
            try{
                Tema t = service.findOneTema(id);
                System.out.println("Rezultatul gasit este: " + t.toString());
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class UpdateTemaComanda implements Comanda {

        @Override
        public void execute() {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID: ");
                String id = scanner.nextLine();
                System.out.println("Descriere: ");
                String descriere = scanner.nextLine();
                System.out.println("DeadlineWeek: ");
                Integer deadlineWeek = scanner.nextInt();
                scanner.nextLine();
                Tema old = service.findOneTema(id);
                Tema t = service.updateTema(id, descriere, old.getStartWeek(), deadlineWeek); //
                if(t == null){
                    System.out.println("Tema cu id-ul " + id + " a fost updatata!");
                }
                else
                    System.out.println("Nu exista tema cu id-ul " + id + " !");
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
            catch (ValidationException e){
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e) {
                System.out.println("DeadlineWeek-ul trebuie sa fie int!");
            }
        }
    }

    private class AddNotaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID Student: ");
            String idStudent = scanner.nextLine();
            System.out.println("ID Tema: ");
            String idTema = scanner.nextLine();

            System.out.println("Introduceti data: ");
            System.out.println("Anul: ");
            Integer an = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Luna: ");
            Integer luna = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ziua: ");
            Integer zi = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Profesor: ");
            String profesor = scanner.nextLine();
            System.out.println("Valoare nota: ");
            Float valoare = scanner.nextFloat();

            System.out.println("Studentul a avut motivare? Nr sapt: ");
            float motivare = scanner.nextFloat();
            scanner.nextLine();
            System.out.println("Profesorul a intarziat introducerea notei? Nr sapt: ");
            float intarziere = scanner.nextFloat();
            scanner.nextLine();
            System.out.println("Feedback: ");
            String feedback = scanner.nextLine();
            try{
                LocalDate data = LocalDate.of(an, luna, zi);

                Nota n = service.saveNota(idStudent, idTema, data, profesor, valoare, motivare, intarziere, feedback);
                if(n == null){
                    System.out.println("Nota cu id-ul (" + idStudent + ";" + idTema + ") a fost adaugata!");
                }
                else
                    System.out.println("Nota cu id-ul (" + idStudent + ";" + idTema + ") deja adaugata!");
            }
            catch (ValidationException | IllegalArgumentException | ServiceException | RepositoryException e){
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                System.out.println("Introduceti datele cu atentie sporita...");
            }
            catch (DateTimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class DeleteNotaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID Student: ");
            String idStudent = scanner.nextLine();
            System.out.println("ID Tema: ");
            String idTema = scanner.nextLine();
            try{
                Nota n = service.deleteNota(new Pair<>(idStudent, idTema));
                if(n == null){
                    System.out.println("Nu exista nota cu id-ul " +  "(" + idStudent + ";" + idTema + ")");
                }
                else
                    System.out.println("Nota cu id-ul (" + idStudent + ";" + idTema + ") a fost stearsa!");
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FindNotaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID Student: ");
            String idStudent = scanner.nextLine();
            System.out.println("ID Tema: ");
            String idTema = scanner.nextLine();
            try{
                Nota nota = service.findOneNota(new Pair<>(idStudent, idTema));
                System.out.println("Rezultatul este " + nota.toString());
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class UpdateNotaComanda implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID Student: ");
            String idStudent = scanner.nextLine();
            System.out.println("ID Tema: ");
            String idTema = scanner.nextLine();
            System.out.println("Valoare: ");
            Float valoare = scanner.nextFloat();
            scanner.nextLine();
            try{
                Nota nota = service.findOneNota(new Pair<>(idStudent, idTema));
                Nota n = service.updateNota(idStudent, idTema, nota.getData(), nota.getProfesor(), valoare);
                if(n == null){
                    System.out.println("Nota cu id-ul (" + idStudent + ";" + idTema + ")" + " a fost updatat!");
                }
                else
                    System.out.println("Nu exista nota cu id-ul (" + idStudent + ";" + idTema + ")!");
            }
            catch (IllegalArgumentException | RepositoryException e){
                System.out.println(e.getMessage());
            }
            catch (ValidationException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FiltrareTotiStudentiiUneiGrupe implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Grupa: ");
            Integer grupa = scanner.nextInt();
            scanner.nextLine();
            try{
                Iterable<Student> listaStudenti = service.getStudentByGrupa(grupa);
                int size = 0;
                for (Student student : listaStudenti){
                    size++;
                }
                if(size == 0){
                    System.out.println("Nu exista studenti din grupa " + grupa);
                }
                listaStudenti.forEach(System.out::println);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FiltrareTotiStudentiiCareAuPredatOAnumitaTema implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("IdTema: ");
            String idTema = scanner.nextLine();
            try{
                Iterable<Student> listaStudenti = service.getStudent(service.getStudentByTema(idTema));
                int size = 0;
                for (Student student : listaStudenti){
                    size++;
                }
                if(size == 0){
                    System.out.println("Nu exista studenti care au predat tema " + idTema);
                }
                listaStudenti.forEach(System.out::println);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FiltrareTotiStudentiiCareAuPredatOAnumitaTemaUnuiAnumitProfesor implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("IdTema: ");
            String idTema = scanner.nextLine();
            System.out.println("Profesor: ");
            String profesor = scanner.nextLine();
            try{
                Iterable<Student> listaStudenti = service.getStudent(service.getStudentByTemaAndProfesor(idTema,profesor));
                int size = 0;
                for (Student student : listaStudenti){
                    size++;
                }
                if(size == 0){
                    System.out.println("Nu exista studenti care au predat tema " + idTema + " profesorului " + profesor);
                }
                listaStudenti.forEach(System.out::println);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class FiltrareToateNoteleLaOAnumitaTemaDintrOAnumitaSaptamana implements Comanda {
        @Override
        public void execute() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("IdTema: ");
            String idTema = scanner.nextLine();
            System.out.println("Saptamana: ");
            Integer saptamana = scanner.nextInt();
            scanner.nextLine();
            try{
                Iterable<Nota> listaNote = service.getNotaByTemaAndData(idTema, saptamana);
                int size = 0;
                for (Nota nota : listaNote){
                    size++;
                }
                if(size == 0){
                    System.out.println("Nu exista note la tema " + idTema + " in saptamana " + saptamana);
                }
                listaNote.forEach(System.out::println);
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private class ExitComanda implements Comanda {

        @Override
        public void execute() {
            System.out.println("Sayonara...");
            System.exit(0);
        }
    }
}
