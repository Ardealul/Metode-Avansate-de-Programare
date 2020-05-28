package repository.xml;

import domain.Tema;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TemaXMLRepository extends AbstractXMLRepository<String, Tema> {

    public TemaXMLRepository(Validator<Tema> validator, String fileName) {
        super(validator, fileName);
    }

    @Override
    public Element createElementFromEntity(Document document, Tema entity) {
        Element tema = document.createElement("Tema");
        tema.setAttribute("ID", entity.getId());
        tema.setAttribute("Descriere", entity.getDescriere());
        tema.setAttribute("StartWeek", Integer.toString(entity.getStartWeek()));
        tema.setAttribute("DeadlineWeek", Integer.toString(entity.getDeadlineWeek()));
        return tema;
    }

    @Override
    public Tema createEntityFromElement(Element element) {
        String id = element.getAttribute("ID");
        String descriere = element.getAttribute("Descriere");
        Integer startWeek = Integer.parseInt(element.getAttribute("StartWeek"));
        Integer deadlineWeek = Integer.parseInt(element.getAttribute("DeadlineWeek"));
        return new Tema(id, descriere, startWeek, deadlineWeek);
    }
}
