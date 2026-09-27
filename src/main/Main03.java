package main;

import models.Jogo03;
import models.Jogo04;
import util.Utilitarios;

public class Main03 {

   public static void main(String[] args) {
        
        Jogo03 jogo = new Jogo03();

        for(;;){

            System.out.println("OPÇÕES");
            System.out.println("1 - Adicionar robô");
            System.out.println("2 - Adicionar fruta");
            System.out.println("3 - Iniciar jogo");
            System.out.println("Insira sua opção: ");
            int escolha = Utilitarios.inteiroValido();

            switch (escolha) {
                case 1: jogo.adicionarRobo();break;
                case 2: jogo.posicionarAlimento();break;
                case 3: {

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
                    

                    if(op == 1){
                        jogo = new Jogo04();

                    }else if( op == 2){
                        return;
                    }
                }

                default: System.out.println("Opção inválida."); break;
            }
        }
    }

}
