package models;

import java.util.concurrent.ThreadLocalRandom;

public class Robo02 extends Robo01{

    private int qtdMovimentosInvalidos;
    private int qtdMovimentosValidos;
    private boolean encontrouObstaculo;

    public Robo02(Cor cor){
        super(cor);
        this.qtdMovimentosInvalidos = 0;
        this.qtdMovimentosValidos = 0;
        this.encontrouObstaculo = false;
    }

    public void setEncontrouObstaculo(boolean encontrouObstaculo){
        this.encontrouObstaculo = encontrouObstaculo;
    }

    public boolean getEncontrouObstaculo(){
        return this.encontrouObstaculo;
    }

    public void setQtdMovimentosValidos(int qtdMovimentosValidos){
        this.qtdMovimentosValidos = qtdMovimentosValidos;
    }

    public int getQtdMovimentosValidos(){
        return this.qtdMovimentosValidos;
    }

    public void setQtdMovimentosInvalidos(int qtdMovimentosInvalidos){
        this.qtdMovimentosInvalidos = qtdMovimentosInvalidos;
    }

    public int getQtdMovimentosInvalidos(){
        return this.qtdMovimentosInvalidos;
    }

    public boolean mover(){

        Movimento novoMovimento = null;
        int posX = this.posX;
        int posY = this.posY;

        int numTipoMovimento = ThreadLocalRandom.current().nextInt(1, 5);

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
