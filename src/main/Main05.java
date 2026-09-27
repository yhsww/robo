package main;

import util.Utilitarios;

public class Main05 {

    public static void main(String[] args) {
        
        for(;;){

            System.out.println("Bem-vindo ao Fruit Bot!");
            System.out.println("1 - Um único robô");
            System.out.println("2 - Dois robôs randômicos");
            System.out.println("3 - Um robô inteligente e um robô randômico (sem obstáculos)");
            System.out.println("4 - Um robô inteligente e um robô randômico (com obstáculos)");
            System.out.println("5 - Sair");
            System.out.println("Escolha um modo de jogo: ");
            int escolha = Utilitarios.inteiroValido();

            switch (escolha) {
                case 1: Main01 modoJogo01 = new Main01(); break;
                case 2: Main02 modoJogo02 = new Main02(); break;
                case 3: Main03 modoJogo03 = new Main03(); break;
                case 4: Main04 modoJogo04 = new Main04(); break;
                case 5: return;
                default: System.out.println("Opção inválida!"); break;
            }

        }
        
    }
    
}
