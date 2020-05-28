package repository.xml;

import domain.Student;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class StudentXMLRepository extends AbstractXMLRepository<String, Student> {

    public StudentXMLRepository(Validator<Student> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Element createElementFromEntity(Document document, Student entity) {
        Element student = document.createElement("Student");
        student.setAttribute("ID", entity.getId());
        student.setAttribute("Nume", entity.getNume());
        student.setAttribute("Prenume", entity.getPrenume());
        student.setAttribute("Email", entity.getEmail());
        student.setAttribute("Grupa", Integer.toString(entity.getGrupa()));
        student.setAttribute("CadruDidacticLaborator", entity.getCadruDidacticIndrumatorLab());
        return student;
    }

    @Override
    public Student createEntityFromElement(Element element) {
        String id = element.getAttribute("ID");
        String nume = element.getAttribute("Nume");
        String prenume = element.getAttribute("Prenume");
        String email = element.getAttribute("Email");
        Integer grupa = Integer.parseInt(element.getAttribute("Grupa"));
        String cadruDidacticLaborator = element.getAttribute("CadruDidacticLaborator");

        return new Student(id, nume, prenume, email, grupa, cadruDidacticLaborator);
    }

}
