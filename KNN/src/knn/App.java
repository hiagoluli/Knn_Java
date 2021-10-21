//package knn;

import java.lang.Math;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class App { 
    //calcula distancia euclidiana
    static double distancia(Musica music1, Musica music2) {  
        double soma = 0;
        for(int i=0; i<music1.getAtributos().size();i++ ){
            soma = soma + Math.pow((music1.getAtributos().get(i) - music2.getAtributos().get(i)),2 );
        }
        return Math.sqrt(soma);
    }

    public static void selectionSort(double[] array, int[] vetorOrdenado) {
        for (int fixo = 0; fixo < array.length - 1; fixo++) {
            int menor = fixo;
            vetorOrdenado[0] = fixo;
            for (int i = menor + 1; i < array.length; i++) {
                if (array[i] < array[menor]) {
                    menor = i;
                }
            }
            if (menor != fixo) {
                double aux = array[fixo];
                int indice = fixo;
                array[fixo] = array[menor];
                vetorOrdenado[fixo] = menor; 
                array[menor] = aux;
                vetorOrdenado[menor] = indice;
            }
        }
    }
    
    static String classificacao(ArrayList<Musica> musicas, Musica proxima, int k){
        //distancia de cada musica do conjunto de treinamento para a musica teste
        String classeResultado = "";

        int qtdeMusicas = musicas.size();
        //System.out.println("Quantidade de musicas " + qtdeMusicas);
        double distAmostra[] = new double[qtdeMusicas];
        int vetorOrdenado[] = new int[qtdeMusicas];

        for(int i=0; i<qtdeMusicas;i++){
            double dist = distancia(musicas.get(i), proxima);
            distAmostra[i] = dist;             
        } 
        System.out.println("ANTES");
        for(int i = 0; i < distAmostra.length; i++){
            System.out.println(distAmostra[i]);
        }
        
        selectionSort(distAmostra,vetorOrdenado);
        
        System.out.println("DEPOIS");
        for(int i = 0; i < distAmostra.length; i++){
            System.out.println(distAmostra[i]);
        }
        
        int contadorDeClasse[] = new int[6];
        int vizinhos = 0;
        for(int i=0; i < distAmostra.length; i++){
            if(vizinhos > k){
                break;
            }
            
            String classe = musicas.get(vetorOrdenado[i]).getClasse();
            //System.out.println("musica classe " + classe); //Para Teste

            switch (classe) {
                case "rap":
                    contadorDeClasse[0]++;
                break;
                case "axe":
                    contadorDeClasse[1]++;
                break;
                case "sertanejo":
                    contadorDeClasse[2]++;
                break;
                case "samba":
                    contadorDeClasse[3]++;
                break;
                case "forro":
                    contadorDeClasse[4]++;
                break;
                case "bossa_nova":
                    contadorDeClasse[5]++;
                break;   
            }
            vizinhos++;
        } 
        /*
        for(int i = 0; i < contadorDeClasse.length; i++){
            System.out.println(contadorDeClasse[i]);
        }
        */
        int maior = 0;
        int indiceMaior = -1;
        for (int i = 0; i < contadorDeClasse.length; i++) {
            if (contadorDeClasse[i] > maior) {
                maior = contadorDeClasse[i];
                indiceMaior = i;
            }
        }
        
        switch (indiceMaior) {
            case 0:
                classeResultado = "rap";
            break;
            case 1:
                classeResultado = "axe";
            break;
            case 2:
                classeResultado = "sertanejo";
            break;
            case 3:
                classeResultado = "samba";
            break;
            case 4:
                classeResultado = "forro";
            break;
            case 5:
                classeResultado = "bossa_nova";
            break;   
        }
        return classeResultado;
    }

    static ArrayList<Musica> lerArquivo(String nomearq){
        File arq = new File(nomearq);
        Scanner sc = null;
        ArrayList<Musica> lista = new ArrayList<Musica>();
        try {
            sc = new Scanner(arq);
            String linha;
            //String linha = sc.nextLine();
            int contlinha = 0;
            while(sc.hasNextLine()){
                linha = sc.nextLine();
                //System.out.println(linha); //Para Teste
                String[] vetor = linha.split(",");
                ArrayList<Double> atrDoubles = new ArrayList<>();
                double atr;
                String classe;
                classe = vetor[vetor.length-1];
                contlinha++;
                Musica musica = new Musica();
                musica.setClasse(classe);
                for(int i=0; i<vetor.length - 1; i++){     
                    atr = Double.parseDouble(vetor[i]);
                    atrDoubles.add(atr);
                }
                musica.addAtributos(atrDoubles);
                lista.add(musica);          
                //System.out.println(musica.getClasse()); //para Teste
                //System.out.println(musica.getAtributos()); //para Teste
            }
            System.out.println(contlinha);          
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        /* //Para Teste
        for(int i=0;i<lista.size();i++){
            System.out.println(lista.get(i).getClasse());
        }
        */
        return lista;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String arqTrain = "c:\\Users\\hiago\\OneDrive\\Área de Trabalho\\bases\\bases\\train_592.txt";
        String arqTest = "c:\\Users\\hiago\\OneDrive\\Área de Trabalho\\bases\\bases\\test_59.DATA";

        //String arqTrain = "c:\\Users\\hiago\\Desktop\\bases\\bases\\train_59.DATA";
        //String arqTest = "c:\\Users\\hiago\\Desktop\\bases\\bases\\test_59.DATA";

        ArrayList<Musica> listaTreinamento = new ArrayList<Musica>();
        ArrayList<Musica> listaTeste = new ArrayList<Musica>();

        listaTreinamento = lerArquivo(arqTrain);
        listaTeste = lerArquivo(arqTest);    

        System.out.println(listaTeste.get(0).getAtributos());
     
        int k = 10;
        int acertos = 0;
        
        for(int i=0; i<listaTeste.size();i++){
            String resultado = classificacao(listaTreinamento,listaTeste.get(i),k);
            String esperada = listaTeste.get(i).getClasse();
            System.out.println("Classe esperada: " + listaTeste.get(i).getClasse());
            System.out.println("Classe obtida: " + resultado);
            if(esperada.equals(resultado)){
                acertos++;
            }
        }

        System.out.println("Acertos: " + acertos + " de " + listaTeste.size() + " Testes.");
        
    }
    
}
