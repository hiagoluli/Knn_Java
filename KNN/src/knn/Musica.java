//package knn;

import java.util.ArrayList;

public class Musica {
    private String classe;
    private ArrayList<Double> atributos = new ArrayList<Double>();

    public Musica(){

    }

    public Musica(String classe, ArrayList<Double> atributos){
        this.classe = classe;
        this.atributos = atributos;
    }

    public String getClasse(){
        return classe;
    }

    public void setClasse(String classe){
        this.classe = classe;
    }

    ArrayList<Double> getAtributos() {
        return atributos;
    }
    
    public void addAtributos(ArrayList<Double> atributos){
        this.atributos = atributos;
    }
}
