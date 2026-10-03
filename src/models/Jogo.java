package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public abstract class Jogo {

    protected Tabuleiro tabuleiro;
    protected List<Robo> robos;
    
    protected Fruta fruta;

    public Jogo(){
        this.tabuleiro = new Tabuleiro();
        this.robos = new ArrayList<>();
        this.fruta = new Fruta();
    }

    protected boolean autorizarPartida(){

        if(this.robos.isEmpty()){
            System.out.println("Adicione robôs para iniciar a partida!");
            return false;
        }

        if(this.fruta == null){
            System.out.println("Adicione uma fruta para iniciar a partida!");
            return false;
        }

        return true;

    }

    protected void pausa(){

        try{
            Thread.sleep(1400);

        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        
    }

    public void moverAleatorio(Robo robo){

        boolean seMoveu = false;

        while(!seMoveu){
           try{

                robo.moverRandomico();
                robo.setQtdMovimentosValidos(robo.getQtdMovimentosValidos() + 1);
                seMoveu = true;
           }catch(MovimentoInvalidoException e){

                robo.setQtdMovimentosInvalidos(robo.getQtdMovimentosInvalidos() + 1);
                System.out.println(e.getMessage());
           }
        }

    }

    public boolean adicionarFruta(){
        
        System.out.println("Coordenada X");
        int posX = tabuleiro.definirCoordenada();
        System.out.println("Coordenada Y");
        int posY = tabuleiro.definirCoordenada();

        if(this.tabuleiro.validarPosicao(posX, posY)){
            this.fruta.setPosX(posX);
            this.fruta.setPosY(posY);
            this.tabuleiro.adicionarFruta(this.fruta);
            System.out.println("Fruta adicionada com sucesso!");
            return true;
        }
        
        System.out.println("Posição inválida!");
        return false;

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

        while(escolha <= 0 || escolha > Cor.values().length || jaTemRoboDessaCor(escolha)){

            System.out.println("A cor escolhida não está disponível. Selecione uma cor válida: ");
            escolha = Utilitarios.inteiroValido();

        }


        switch (escolha) {

            case 1: novoRobo = new Robo(Cor.RED); break;
            case 2: novoRobo = new Robo(Cor.BLUE); break;
            case 3: novoRobo = new Robo(Cor.GREEN); break;
            case 4: novoRobo = new Robo(Cor.WHITE); break;
            case 5: novoRobo = new Robo(Cor.DARK); break;
            case 6: novoRobo = new Robo(Cor.YELLOW); break;
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

    public abstract void iniciarPartida();

    protected boolean jaTemRoboDessaCor(int numCor){

        return this.robos.stream().anyMatch(robo -> robo.getCor().getNumCor() == numCor);
    }

   
}
