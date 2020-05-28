package repository;

import domain.Entity;
import domain.validators.ValidationException;
import domain.validators.Validator;

import java.util.HashMap;

public abstract class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E> {
    protected HashMap<ID, E> entities;
    protected Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.entities = new HashMap<ID, E>();
        this.validator = validator;
    }

    @Override
    public E findOne(ID id) {
        if(id.equals("") || id == null) {
            throw new IllegalArgumentException("ID invalid!");
        }
        if(entities.get(id) == null){
            throw new RepositoryException("Nu exista entitate cu id-ul " + id + "!") ;
        }
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if(entity == null){
            throw new IllegalArgumentException("Entitate invalida!");
        }
        E oldValue = entities.get(entity.getId());
        if(oldValue == null){
            validator.validate(entity);
            entities.put(entity.getId(), entity);
            return null;
        }
        return oldValue;
        //else throw new RepositoryException("Entitatea cu id-ul " + entity.getId() + " deja salvat!");
    }

    @Override
    public E delete(ID id) {
        if(id == null){
            throw new IllegalArgumentException("ID invalid!");
        }
        if(entities.containsKey(id)){
            return entities.remove(id);
        }
        return null;
        //else throw new RepositoryException("Nu exista entitatea cu id-ul cutare in repository!");
    }

    @Override
    public E update(E entity){
        if(entity==null)
            throw new IllegalArgumentException("Entitate invalida!");

        E oldEntity = findOne(entity.getId());
        if(oldEntity != null) {
            validator.validate(entity);
            entities.put(entity.getId(), entity);
            return null; //
        }
        return oldEntity;
        //else throw new RepositoryException("Nu exista entitatea in repository!");
    }
}
