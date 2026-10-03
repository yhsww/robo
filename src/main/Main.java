package main;

import models.Jogo;
import models.Jogo01;
import models.Jogo02;
import models.Jogo03;
import models.Jogo04;
import util.Utilitarios;

public class Main {

    public static void main(String[] args) {

        System.out.println("Bem-vindo ao Fruit Bot!");
        System.out.println("Definir modo de jogo");
        System.out.println("1 - Um único robô");
        System.out.println("2 - Dois robôs randômicos");
        System.out.println("3 - Um robô inteligente e um robô normal");
        System.out.println("4 - Dois robôs + obstáculos");
        int escolha = Utilitarios.inteiroValido();

        Jogo jogo;

        switch (escolha) {
            case 1: jogo = new Jogo01();break;
            case 2: jogo = new Jogo02();break;
            case 3: jogo = new Jogo03(); break;
            case 4: jogo = new Jogo04(); break;
            default: System.out.println("Opção indisponível!"); return;
        }

        System.out.println("---------------------");
        System.out.println("Adicionando robô 1");
        boolean addRobo1 = false;

        while(!addRobo1){
            addRobo1 = jogo.adicionarRobo();
        }

        
        if(!(jogo instanceof Jogo01)){

            System.out.println("---------------------");
            System.out.println("Adicionar robô 2");

            boolean addRobo2 = false;
            while (!addRobo2) {
                addRobo2 = jogo.adicionarRobo();
            }
        
        }

        boolean addFruta = false;
        
        System.out.println("---------------------");
        System.out.println("Adicionando fruta");
        while(!addFruta){
            addFruta = jogo.adicionarFruta();
        }

        if(jogo instanceof Jogo04 j4){

            System.out.println("---------------------");
            System.out.println("Adicionando obstáculos");

            System.out.println("Quantos obstáculos deseja adicionar?");
            int qtdObstaculos = Utilitarios.inteiroValido();
            
            
            for(int i = 1; i <= qtdObstaculos; i++){
                
                boolean addObstaculo = false;
                while (!addObstaculo) {
                    System.out.println("\nObstáculo " + i);
                    addObstaculo = j4.adicionarObstaculo();
                }        
            }
            
        }

        System.out.println("---------------------");
        System.out.println("Iniciando jogo");
        jogo.iniciarPartida();
        System.out.println("---------------------");
        System.out.println("FIM DE JOGO");
    }
    
}
