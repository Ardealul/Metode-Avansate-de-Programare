package repository;

import domain.Student;
import domain.validators.Validator;

public class StudentRepository extends InMemoryRepository<String, Student> {

    public StudentRepository(Validator<Student> validator) { super(validator); }

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
        return oldValue;
    }
}
