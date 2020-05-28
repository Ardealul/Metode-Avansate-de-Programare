package domain.validators;

import domain.Student;

public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student entity) throws ValidationException {
        String errors = "";
        if(entity.getId() == null || entity.getId().equals("")){
            errors += "Id student invalid!\n";
        }
        if(entity.getNume().equals("")){
            errors += "Nume student invalid!\n";
        }
        if(entity.getPrenume().equals("")){
            errors += "Prenume student invalid!\n";
        }
        if(entity.getEmail().equals("")){
            errors += "Email student invalid!\n";
        }
        if(entity.getGrupa() <= 0){
            errors += "Grupa student invalida!\n";
        }
        if(entity.getCadruDidacticIndrumatorLab().equals("")){
            errors += "Cadru didactic invalid!\n";
        }
        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
