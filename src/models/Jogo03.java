package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public class Jogo03 extends Jogo{

    public Jogo03(){
        super();
    }

    private boolean todosEncontraram(){

        for(Robo robo: this.robos){
            if(!robo.encontrouAlimento(this.fruta)){
                return false;
            }
        }

        return true;

    }

    public boolean adicionarRobo(){
        
        Robo novoRobo = null;
        List<Cor> coresDisponiveis = new ArrayList<>(List.of(Cor.values()));

        if(!this.robos.isEmpty()){
            
            for(Robo robo: this.robos){
                coresDisponiveis.remove(robo.getCor());
            }
        }

        System.out.println("CORES DISPONÍVEIS");
        for(Cor cor: coresDisponiveis){
            System.out.println(cor.getNumCor() + " - " + cor.getTipoCor());
        }

        System.out.println("Selecione o número da cor desejada para o robô: ");
        int escolha = Utilitarios.inteiroValido();

        while(escolha <= 0 || escolha > Cor.values().length){

            System.out.println("A cor escolhida não está disponível. Selecione uma cor válida: ");
            escolha = Utilitarios.inteiroValido();
        }

        switch (escolha) {

            case 1: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.RED);
                }else{
                    novoRobo = new RoboInteligente(Cor.RED);
                }
                 break;
                }
            case 2: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.BLUE);
                }else{
                    novoRobo = new RoboInteligente(Cor.BLUE);
                }
                 break;
                }
            case 3: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.GREEN);
                }else{
                    novoRobo = new RoboInteligente(Cor.GREEN);
                }
                 break;
                }
            case 4: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.WHITE);
                }else{
                    novoRobo = new RoboInteligente(Cor.WHITE);
                }
                 break;
                }
            case 5: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.DARK);
                }else{
                    novoRobo = new RoboInteligente(Cor.DARK);
                }
                 break;
                }
            case 6: {

                if(this.robos.isEmpty()){
                    novoRobo = new Robo(Cor.YELLOW);
                }else{
                    novoRobo = new RoboInteligente(Cor.YELLOW);
                }
                 break;
                }
        }

         System.out.println("Coordenada X");
        int posX = tabuleiro.definirCoordenada();
         System.out.println("Coordenada Y");
        int posY = tabuleiro.definirCoordenada();
        novoRobo.setPosX(posX);
        novoRobo.setPosY(posY);

        if(this.tabuleiro.adicionarRobo(novoRobo)){
            this.robos.add(novoRobo);
            System.out.println("Robô adicionado com sucesso!");
            return true;
        }

        System.out.println("Posição inválida!");
        return false;
        
    }

    public void iniciarPartida(){

        if(!autorizarPartida()){
            return;
        }

        int partida = 1;
        while(!todosEncontraram()){

            System.out.println();
            System.out.println("PARTIDA " + partida);
            for(Robo robo : this.robos){
                
                if(robo.encontrouAlimento(fruta)) continue;
                moverAleatorio(robo);

            }  
            
            pausa();
            this.tabuleiro.criarTabuleiro();
           

            partida++;
        }


        for(Robo robo: this.robos){
            System.out.println("------------");
            System.out.println(robo.toString());
        }

        System.out.println("Ambos os robôs alcaçaram a fruta!");

        }

        
    }
    
