package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public class Jogo03 extends Jogo01{

    private RoboInteligente roboInteligente;
    private Robo02 roboNormal;

    public void adicionarRobo(){

        if(this.roboInteligente != null && roboNormal != null){
        
            String resp = null;

            while(!resp.equalsIgnoreCase("s") && !resp.equalsIgnoreCase("n")){
                System.out.println("Os robôs já foram configurados. Deseja realizar alterações? [s/n]");
                resp = Utilitarios.stringValida();
            }

            if(resp.equalsIgnoreCase("n")){
                return;
            }else{
                
                this.roboInteligente = null;
                this.roboNormal = null;
            }

        }

        Cor corNova = null;

        List<Cor> coresDisponiveis = new ArrayList<>(List.of(Cor.values()));

        for(Cor cor: Cor.values()){
            if(this.roboInteligente.getCor().equals(cor)){
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

        if(this.roboInteligente == null){
            this.roboInteligente = new RoboInteligente(corNova);
        }else if(this.roboNormal == null){
            this.roboNormal = new Robo02(corNova);
        }

    }

    public void iniciarJogo(){

          if(this.roboInteligente == null || this.roboNormal == null){
            System.out.println("Adicione robôs para iniciar o jogo!");
            return;
        }

        if(this.fruta == null){
            System.out.println("Adiciona uma fruta para iniciar o jogo!");
            return;
        }

        while(!(this.roboInteligente.encontrouAlimento(this.fruta) && !this.roboNormal.encontrouAlimento(this.fruta))){

            boolean lancaExcecao1 = this.roboInteligente.mover();
            boolean lancaExcecao2 = this.roboNormal.mover();

            while(lancaExcecao1){
                
                new MovimentoInvalidoException();
                lancaExcecao1 = this.roboInteligente.mover();
                 this.roboInteligente.setQtdMovimentosInvalidos(this.roboInteligente.getQtdMovimentosInvalidos() + 1);
            }

              if(!lancaExcecao1){
                this.roboInteligente.setQtdMovimentosValidos(this.roboInteligente.getQtdMovimentosValidos() + 1);
            }

            while(lancaExcecao2){
                new MovimentoInvalidoException();
                lancaExcecao2 = this.roboNormal.mover();
                this.roboNormal.setQtdMovimentosInvalidos(this.roboNormal.getQtdMovimentosInvalidos() + 1);
            }

            
            if(!lancaExcecao2){
                this.roboNormal.setQtdMovimentosValidos(this.roboNormal.getQtdMovimentosValidos() + 1);
            }


        }

        System.out.println("Os robôs encontraram a fruta!");

        System.out.println("\n\n");

         System.out.println("-----------------------------");
        System.out.println();
        System.out.println("Robô Inteligente - " + this.roboInteligente.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.roboInteligente.getQtdMovimentosInvalidos() + this.roboInteligente.getQtdMovimentosValidos());
        System.out.println("-----------------------------");
        System.out.println("Robô Normal - " + this.roboNormal.getCor().getTipoCor());
        System.out.println("Quantidade de movimentos válidos: " + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("Quantidade de movimentos inválidos: " + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("Total de movimentos: " + this.roboNormal.getQtdMovimentosInvalidos() + this.roboNormal.getQtdMovimentosValidos());
        System.out.println("\n\n");
        System.out.println("FIM DE JOGO");



    }

    
}
