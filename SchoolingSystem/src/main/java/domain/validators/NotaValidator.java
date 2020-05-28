package domain.validators;

import domain.Nota;

public class NotaValidator implements Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
        String errors = "";
        if(entity.getId() == null || entity.getId().equals("")){
            errors += "Id-ul notei nu este valid!";
        }
        if(entity.getId().fst == null || entity.getId().equals("")){
            errors += "Id-ul studentului nu este valid!";
        }
        if(entity.getId().snd == null || entity.getId().equals("")){
            errors += "Id-ul temei nu este valid!";
        }
        if(entity.getProfesor() == null || entity.getProfesor().equals("")){
            errors += "Profesorul nu este valid!";
        }
        if(entity.getData() == null || entity.getData().equals("")){
            errors += "Data nu este valida!";
        }
        if(entity.getNota() < 0 || entity.getNota() > 10){
            errors += "Nota trebuie sa fie intre 0 si 10!";
        }
        if(!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
