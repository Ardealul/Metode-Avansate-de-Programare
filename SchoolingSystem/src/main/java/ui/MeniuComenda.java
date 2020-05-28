package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MeniuComenda implements Comanda {
    private String nameMeniu;
    private Map<String, Comanda> map = new TreeMap<>();

    public MeniuComenda(String nameMeniu) {
        this.nameMeniu = nameMeniu;
    }


    @Override
    public void execute() {
        map.keySet().forEach(System.out::println);
    }

    public void addComanda(String descriereComanda, Comanda comanda){
        map.put(descriereComanda, comanda);
    }

    public List<Comanda> getComenzi(){
        return new ArrayList<>(map.values());
    }

    public String getNameMeniu(){
        return nameMeniu;
    }
}
