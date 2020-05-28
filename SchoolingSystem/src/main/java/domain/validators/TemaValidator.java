package domain.validators;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {
    @Override
    public void validate(Tema entity) throws ValidationException {
        String errors = "";
        if(entity.getId() == null || entity.getId().equals("")){
            errors += "Id tema invalid!\n";
        }
        if(entity.getDescriere().equals("")){
            errors += "Descriere tema invalida!\n";
        }
        if(entity.getStartWeek() < 1 || entity.getStartWeek() > 14){
            errors += "StartWeek-ul trebuie sa fie intre 1 si 14!\n";
        }
        if(entity.getDeadlineWeek() < 1 || entity.getDeadlineWeek() > 14){
            errors += "Deadline-ul trebuie sa fie intre 1 si 14!\n";
        }
        if(entity.getStartWeek() >= entity.getDeadlineWeek()){
            errors += "StartWeek mai mare sau egal decat DeadlineWeek-ul!\n";
        }
        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
