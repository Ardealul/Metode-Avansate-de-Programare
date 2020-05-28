package fileRepository;

import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class FileRepository<E extends Entity<ID>, ID> extends InMemoryRepository<ID, E> implements CrudFileRepository<ID, E> {
    private String fileName;

    public FileRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    @Override
    public E findOne(ID id) {
        E result = super.findOne(id);
        saveAllToFile();
        return result;
    }

    @Override
    public Iterable<E> findAll() {
        return super.findAll();
    }

    @Override
    public E save(E entity) throws ValidationException {
        E result = super.save(entity);
        saveOneToFile(entity);
        return result;
    }

    @Override
    public E delete(ID id) {
        E result = super.delete(id);
        saveAllToFile();
        return result;
    }

    @Override
    public void saveAllToFile() {
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(fileName))){
            for(E entity: super.findAll()){
                //bufferedWriter.newLine();
                bufferedWriter.write(entity.toString());
                bufferedWriter.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveOneToFile(E entity) {
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.APPEND)){
            //bufferedWriter.newLine();
            bufferedWriter.write(entity.toString());
            bufferedWriter.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        Path path = Paths.get(fileName);
        try{
            List<String> inputList = Files.readAllLines(path);
            inputList.forEach(this::parseLine);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public abstract E createEntity(String[] fields);

    private void parseLine(String line){
        try{
            String[] fields = line.split("; ");
            E entity = createEntity(fields);
            super.save(entity);
        }
        catch (ValidationException e){
            e.printStackTrace();
        }
    }
}
