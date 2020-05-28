package fileRepository;

import domain.StructuraAnUniversitar;
import domain.Tema;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.RepositoryException;

import java.time.LocalDate;

public class TemaFileRepository extends FileRepository<Tema, String> {
    private String fileName;

    public TemaFileRepository(Validator<Tema> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Tema createEntity(String[] fields) {
        Tema tema = new Tema(
                fields[0],
                fields[1],
                Integer.parseInt(fields[2]), //StructuraAnUniversitar.getInstance().getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1()),
                Integer.parseInt(fields[3]));
        return tema;
    }

    @Override
    public Tema update(Tema entity) {
        if(entity == null){
            throw new IllegalArgumentException("Entitate invalida!");
        }
        Integer currentWeek = StructuraAnUniversitar.getInstance().getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1());
        //System.out.println(entity.getDeadlineWeek());
        //System.out.println(currentWeek);
        if(entity.getDeadlineWeek() < currentWeek){
            throw new ValidationException("DeadlineWeek mai mare decat currentWeek!");
        }
        validator.validate(entity);
        if(entities.get(entity.getId()) == null){
            throw new RepositoryException("Tema cu id-ul " + entity.getId() + " nu este salvata in repo!");
        }
        if(entities.get(entity.getId()).equals(entity)) {
            return entity;
        }
        Tema oldValue = entities.get(entity.getId());
        oldValue.setDescriere(entity.getDescriere());
        oldValue.setStartWeek(entity.getStartWeek());
        oldValue.setDeadlineWeek(entity.getDeadlineWeek());
        saveAllToFile();
        return oldValue;
    }
}
