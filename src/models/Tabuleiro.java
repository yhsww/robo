package models;

import java.util.ArrayList;
import java.util.List;

import util.Utilitarios;

public class Tabuleiro {

    public static final int DIMENSAO_TABULEIRO = 4;
    private List<Obstaculo> obstaculos;
    private List<Robo> robos;
    private Fruta fruta; 

    public Tabuleiro(){
        this.obstaculos = new ArrayList<>();
        this.robos = new ArrayList<>();
        this.fruta = new Fruta();
    }
    
    public void criarTabuleiro(){

        for (int posY = Tabuleiro.DIMENSAO_TABULEIRO; posY > 0; posY--) {
            for (int posX = 1; posX <= Tabuleiro.DIMENSAO_TABULEIRO; posX++) {
                
                String casa = "[.]";

                if(this.fruta.getPosX() == posX && this.fruta.getPosY() == posY){
                    casa = "[F]";
                }

                for(Obstaculo obstaculo : this.obstaculos){

                    if(obstaculo instanceof Bomba bomba && bomba.getPosX() == posX && bomba.getPosY() == posY){
                        casa = "[B]";
                    }else if(obstaculo instanceof Rocha rocha && rocha.getPosX() == posX && rocha.getPosY() == posY){
                        casa = "[R]";
                    }else{
                        casa = "[.]";
                    }
                }


                for(Robo robo: this.robos){

                    if(!robo.getExplodiu() && robo.getPosX() == posX && robo.getPosY() == posY){
                        casa = "[" + robo.getCor().getTipoCor().charAt(0) + "]";
                    }
                }

                
                System.out.print(casa + " ");
                
            }

            System.out.println();
        }

    }


    public boolean validarPosicao(int posX, int posY){

        boolean naFruta = posX == this.fruta.getPosX() && posY == this.fruta.getPosY();
        boolean outroRobo = this.robos.stream().anyMatch(robo -> robo.getPosX() == posX && robo.getPosY() == posY);
        boolean obstaculo = this.obstaculos.stream().anyMatch(ob -> ob.getPosX() == posX && ob.getPosY() == posY);

        if(naFruta || outroRobo || obstaculo){
            return false;
        }

        return true;
    }

    public int definirCoordenada(){

        System.out.println("Insira as coordenadas de 1 a " + Tabuleiro.DIMENSAO_TABULEIRO + " :");
        int coord = Utilitarios.inteiroValido();

        while(coord <= 0 || coord > Tabuleiro.DIMENSAO_TABULEIRO){
            System.out.println("Selecione coordenada válida: de 1 a 4. Insira novamente: ");
            coord = Utilitarios.inteiroValido();
        }

        return coord;

    }


    public boolean adicionarFruta(Fruta fruta){

        if(validarPosicao(fruta.getPosX(), fruta.getPosY())){
            this.fruta = fruta;
            return true;
        }

        return false;    

    }

    public boolean adicionarObstaculos(Obstaculo obstaculo){

        if(validarPosicao(obstaculo.getPosX(), obstaculo.getPosY())){
            this.obstaculos.add(obstaculo);
            return true;
        }

        return false;
    }

    public boolean adicionarRobo(Robo robo){

        if(validarPosicao(robo.getPosX(), robo.getPosY())){
            this.robos.add(robo);
            return true;
        }

        return false;

    }
    
}
