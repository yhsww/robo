package main;

import models.Jogo;
import models.Jogo01;
import models.Jogo02;
import models.Jogo03;
import models.Jogo04;
import util.Utilitarios;


public class Main06{

    public static void main(String[] args) {

        Jogo jogo = null;
        
        for (;;) {

            System.out.println("Bem-vindo ao Fruit Bot!");
            System.out.println("1 - Um único robô");
            System.out.println("2 - Dois robôs randômicos");
            System.out.println("3 - Um robô inteligente e um robô randômico (sem obstáculos)");
            System.out.println("4 - Um robô inteligente e um robô randômico (com obstáculos)");
            System.out.println("5 - Sair");
            System.out.print("Escolha um modo de jogo: ");
            int escolha = Utilitarios.inteiroValido();

            switch (escolha) {
                case 1: jogo = new Jogo01(); break;
                case 2: jogo = new Jogo02(); break;
                case 3: jogo = new Jogo03(); break;
                case 4: jogo = new Jogo04(); break;
                case 5: return;
                default: System.out.println("Opção inválida!"); return;
            }

            jogo.adicionarRobo();

            if(jogo instanceof Jogo02 || jogo instanceof Jogo03 || jogo instanceof Jogo04){
                jogo.adicionarRobo();
            }

            jogo.posicionarAlimento();

            if(jogo instanceof Jogo04 jogo4){
                jogo4.adicionarObstaculos();
            }

            jogo.iniciarJogo();

            System.out.println("OPÇÕES");
            System.out.println(" 1 - Jogar novamente");
            System.out.println("2 - Finalizar jogo");
            System.out.println("Insira sua opção: ");
            int op = Utilitarios.inteiroValido();

                while(op != 1 && op != 2){
                    System.out.println("Opção inválida. Insira novamente: ");
                    op = Utilitarios.inteiroValido();
                }
            
                if(op == 2) return;
        }
    }

}



