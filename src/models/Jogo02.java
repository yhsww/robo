package models;

import java.util.ArrayList;
import java.util.List;

import models.Cor;
import util.Utilitarios;

public class Jogo02 extends Jogo01{

    private Robo02 robo1, robo2;
   

    //public void posicionarAlimento(); ok

    public void adicionarRobo(){

        if(this.robo1 != null && robo2 != null){
        
            String resp = null;

            while(!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n")){
                System.out.println("Os robôs já foram configurados. Deseja realizar alterações? [s/n]");
                resp = Utilitarios.stringValida();
            }

            if(resp.equalsIgnoreCase("n")){
                return;
            }else{
                
                this.robo1 = null;
                this.robo2 = null;
            }

        }

        Cor corNova = null;

        List<Cor> coresDisponiveis = new ArrayList<>(List.of(Cor.values()));

        for(Cor cor: Cor.values()){
            if(this.robo1.getCor().equals(cor) || this.robo2.getCor().equals(cor)){
                coresDisponiveis.remove(cor);
            }
        }

        System.out.println("CORES DISPONÍVEIS");
        for(Cor cor : coresDisponiveis){
            System.out.println(cor.getNumCor() + " - " + cor.getTipoCor());
        }

        System.out.println("Insira o número correspondente à cor do robô: ");
        int escolha = Utilitarios.inteiroValido();

        corNova = coresDisponiveis.stream().filter(cor -> cor.getNumCor() == escolha).findAny().get();

        if(corNova == null){
            System.out.println("Cor indisponível!");
            return;
        }

        if(this.robo1 == null){
            this.robo1 = new Robo02(corNova);
        }else if(this.robo2 == null){
            this.robo2 = new Robo02(corNova);
        }

    }


    public void iniciarJogo(){

          if(this.robo1 == null || this.robo2 == null){
            System.out.println("Adicione robôs para iniciar o jogo!");
            return;
        }

        if(this.fruta == null){
            System.out.println("Adicione uma fruta para iniciar o jogo!");
            return;
        }

        while(!this.robo1.encontrouAlimento(this.fruta) && !this.robo2.encontrouAlimento(this.fruta)){

            System.out.println("\n\n");

            boolean lancaExcecao1 = this.robo1.mover();
            boolean lancaExcecao2 = this.robo2.mover();

            while(lancaExcecao1){
                
                new MovimentoInvalidoException();
                lancaExcecao1 = this.robo1.mover();
                this.robo1.setQtdMovimentosInvalidos(this.robo1.getQtdMovimentosInvalidos() + 1);
            }

            this.robo1.setQtdMovimentosValidos(this.robo1.getQtdMovimentosValidos() + 1);

            while(lancaExcecao2){

                new MovimentoInvalidoException();
                lancaExcecao2 = this.robo2.mover();
                this.robo2.setQtdMovimentosInvalidos(this.robo2.getQtdMovimentosInvalidos() + 1);

            }

            this.robo2.setQtdMovimentosValidos(this.robo2.getQtdMovimentosValidos() + 1);

        }

        Robo02 vencedor = null;

        if(this.robo1.encontrouAlimento(fruta)){
            vencedor = this.robo1;
        }else if(this.robo2.encontrouAlimento(fruta)){
            vencedor = this.robo2;
        }

        System.out.println("\n\n");
        System.out.println("Parabéns! O robô " + vencedor.getCor().getTipoCor() + " conseguiu alcançar a fruta!");

         System.out.println("-----------------------------");
        System.out.println();
        System.out.println("Robô 1 - " + this.robo1.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.robo1.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.robo1.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.robo1.getQtdMovimentosInvalidos() + this.robo1.getQtdMovimentosValidos());
        System.out.println("-----------------------------");
        System.out.println("Robô 2 - " + this.robo2.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.robo2.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.robo2.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.robo2.getQtdMovimentosInvalidos() + this.robo2.getQtdMovimentosValidos());
        System.out.println("\n\n");
        System.out.println("FIM DE JOGO");

    }
    


    
    
}
