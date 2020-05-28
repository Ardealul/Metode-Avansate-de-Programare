package fileRepository;

import domain.Student;
import domain.validators.Validator;
import repository.RepositoryException;

public class StudentFileRepository extends FileRepository<Student, String> {
    private String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Student createEntity(String[] fields) {
        try{
            Student student = new Student(fields[0], fields[1], fields[2], fields[3], Integer.parseInt(fields[4]),fields[5]);
            return student;
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Student update(Student entity) {
        if(entity == null){
            throw new IllegalArgumentException("Entitate invalida!");
        }
        validator.validate(entity);
        if(entities.get(entity.getId()) == null){
            throw new RepositoryException("Studentul cu id-ul " + entity.getId() + " nu este salvat in repo!");
        }
        if(entities.get(entity.getId()).equals(entity)){
            return entity;
        }
        Student oldValue = entities.get(entity.getId());
        oldValue.setNume(entity.getNume());
        oldValue.setPrenume(entity.getPrenume());
        oldValue.setEmail(entity.getEmail());
        oldValue.setGrupa(entity.getGrupa());
        oldValue.setCadruDidacticIndrumatorLab(entity.getCadruDidacticIndrumatorLab());
        saveAllToFile();
        return oldValue;
    }
}
