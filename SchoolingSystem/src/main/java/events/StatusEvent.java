package events;

public class StatusEvent<E> implements Event{

    private ExecutionStatusEventType type;
    private E entity;

    public StatusEvent(ExecutionStatusEventType type, E entity) {
        this.type = type;
        this.entity = entity;
    }

    public ExecutionStatusEventType getType(){return type;}

    public void setType(ExecutionStatusEventType type){this.type = type;}

    public E getEntity(){return entity;}

    public void setEntity(E entity){this.entity = entity;}
}
