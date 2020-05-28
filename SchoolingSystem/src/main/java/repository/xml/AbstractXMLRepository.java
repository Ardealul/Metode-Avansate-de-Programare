package repository.xml;

import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repository.InMemoryRepository;
import repository.RepositoryException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class AbstractXMLRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String fileName;

    public AbstractXMLRepository(Validator<E> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData(){
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i = 0; i < children.getLength(); i++){
                Node entityElement = children.item(i);
                if(entityElement instanceof Element){
                    E entity = createEntityFromElement((Element) entityElement);
                    super.save(entity);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(){
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("inbox");
            document.appendChild(root);
            super.findAll().forEach(e -> {
                Element element = createElementFromEntity(document, e);
                root.appendChild(element);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.
                    newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(fileName));
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public abstract Element createElementFromEntity(Document document, E entity);

    public abstract E createEntityFromElement(Element element);

    @Override
    public E save(E entity) throws ValidationException {
        E stuff = super.save(entity);
        if(stuff == null){
            writeToFile();
        }
        //return stuff;
        else
            throw new RepositoryException("Studentul exista deja!");
        return stuff;
    }

    @Override
    public E delete(ID id) {
        E stuff = super.delete(id);
        if(stuff != null){
            writeToFile();
        }
        else
            throw new RepositoryException("Studentul nu exista!");
        return stuff;
    }

    //aci ii bai
    @Override
    public E update(E entity) {
        E stuff = super.update(entity);
        if(stuff == null){
            writeToFile();
        }
        else
            throw new RepositoryException("Studentul de modificat nu a fost gasit!");
        return stuff;
    }
}
