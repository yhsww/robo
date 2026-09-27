package models;

import java.util.concurrent.ThreadLocalRandom;

public class RoboInteligente extends Robo02{

    public RoboInteligente(Cor cor){
        super(cor);
    }

    public boolean mover(){


        Movimento novoMovimento = null;
        int posX = this.posX;
        int posY = this.posY;

        int numTipoMovimento = 0;

        while( (this.movimento != null && numTipoMovimento == this.movimento.getNumTipoMovimento()) || numTipoMovimento == 0){
            numTipoMovimento = ThreadLocalRandom.current().nextInt(1, 5);
        }
        

        if(numTipoMovimento == Movimento.UP.getNumTipoMovimento()){

            novoMovimento = Movimento.UP;
            posY += 1;

        }else if(numTipoMovimento == Movimento.DOWN.getNumTipoMovimento()){

            novoMovimento = Movimento.DOWN;
            posY -= 1;
            
        }else if(numTipoMovimento == Movimento.RIGHT.getNumTipoMovimento()){

            novoMovimento = Movimento.RIGHT;
            posX += 1;

        }else if(numTipoMovimento == Movimento.LEFT.getNumTipoMovimento()){

            novoMovimento = Movimento.LEFT;
            posX -= 1;
        }

        if((posX < 0 || posX > Tabuleiro.DIMENSAO_TABULEIRO) || (posY < 0 || posY > Tabuleiro.DIMENSAO_TABULEIRO)){
            return false;
        }

        this.movimento = novoMovimento;
        this.posX = posX;
        this.posY = posY;
        return true;

    }


    
}
