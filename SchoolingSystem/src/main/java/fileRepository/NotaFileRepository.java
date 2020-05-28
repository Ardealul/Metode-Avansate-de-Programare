package fileRepository;

import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.validators.Validator;
import repository.RepositoryException;

import java.time.LocalDate;

public class NotaFileRepository extends FileRepository<Nota, Pair<String, String>> {
    private String fileName;

    public NotaFileRepository(Validator<Nota> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Nota createEntity(String[] fields) {
        Nota nota = new Nota(new Pair<>(fields[0],fields[1]),
                LocalDate.parse(fields[2]),
                fields[3],
                Float.parseFloat(fields[4])
                );
        return nota;
    }

    @Override
    public Nota update(Nota entity) {
        if(entity == null){
            throw new IllegalArgumentException("Entitate invalida!");
        }
        validator.validate(entity);
        if(entities.get(entity.getId()) == null){
            throw new RepositoryException("Nota cu id-ul " + entity.getId() + " nu este salvata in repo!");
        }
        if(entities.get(entity.getId()).equals(entity)){
            return entity;
        }
        Nota oldValue = entities.get(entity.getId());
        oldValue.setNota(entity.getNota());
        oldValue.setData(entity.getData());
        saveAllToFile();
        return oldValue;
    }
}
