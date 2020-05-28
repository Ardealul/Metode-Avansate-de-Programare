package repository.xml;

import com.sun.tools.javac.util.Pair;
import domain.Nota;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;


public class NotaXMLRepository extends AbstractXMLRepository<Pair<String, String>, Nota> {

    public NotaXMLRepository(Validator<Nota> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Element createElementFromEntity(Document document, Nota entity) {
        Element nota = document.createElement("Nota");
        nota.setAttribute("IDStudent", entity.getId().fst);
        nota.setAttribute("IDTema", entity.getId().snd);
        nota.setAttribute("Data", entity.getData().toString());
        nota.setAttribute("Profesor", entity.getProfesor());
        nota.setAttribute("ValoareNota", entity.getNota().toString());
        return nota;
    }

    @Override
    public Nota createEntityFromElement(Element element) {
        String idStudent = element.getAttribute("IDStudent");
        String idTema = element.getAttribute("IDTema");
        Pair<String, String> id = new Pair<>(idStudent, idTema);
        LocalDate date = LocalDate.parse(element.getAttribute("Data"));
        String profesor = element.getAttribute("Profesor");
        Float nota = Float.parseFloat(element.getAttribute("ValoareNota"));
        return new Nota(id, date, profesor, nota);
    }
}
