package models;

import java.util.concurrent.ThreadLocalRandom;

public class Robo {

    protected int posX;
    protected int posY;
    protected int posXAnterior;
    protected int posYAnterior;
    protected Movimento movimento;
    protected boolean explodiu;
    protected int qtdMovimentosValidos;
    protected int qtdMovimentosInvalidos;
    protected Cor cor;

    public Robo(Cor cor){
        this.cor =cor;
        this.posX = 0;
        this.posY = 0;
        this.posXAnterior = 0;
        this.posYAnterior = 0;
        this.qtdMovimentosInvalidos = 0;
        this.qtdMovimentosValidos = 0;
        this.explodiu = false;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public void setPosXAnterior(int posXAnterior){
        this.posXAnterior = posXAnterior;
    }

    public void setPosYAnterior(int posYAnterior){
        this.posYAnterior = posYAnterior;
    }

    public void setQtdMovimentosValidos(int qtdMovimentosValidos){
        this.qtdMovimentosValidos = qtdMovimentosValidos;
    }

    public void setQtdMovimentosInvalidos(int qtdMovimentosInvalidos){
        this.qtdMovimentosInvalidos = qtdMovimentosInvalidos;
    }

    public int getQtdMovimentosValidos(){
        return this.qtdMovimentosValidos;
    }

     public int getQtdMovimentosInvalidos(){
        return this.qtdMovimentosInvalidos;
    }


    public void setExplodiu(boolean explodiu){
        this.explodiu = explodiu;
    }

    public void setCor(Cor cor){
        this.cor = cor;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public Cor getCor(){
        return this.cor;
    }

    public boolean getExplodiu(){
        return this.explodiu;
    }

    public void setMovimento(Movimento movimento){
        this.movimento = movimento;
    }

    public Movimento getMovimento(){
        return this.movimento;
    }

    public void moverRandomico() throws MovimentoInvalidoException{

        mover(ThreadLocalRandom.current().nextInt(1, 5));

    }

    public void mover(String direcao) throws MovimentoInvalidoException{

        direcao.trim().toLowerCase();

        switch (direcao) {

            case "up": mover(1); break;
            case "down": mover(2); break;
            case "right": mover(3); break;
            case "left": mover(4); break;
            default: throw new MovimentoInvalidoException("Movimento inválido: " + direcao + " inexistente!");
        }


    }

    public void mover(int direcao) throws MovimentoInvalidoException{
            
        this.posXAnterior = this.posX;
        this.posYAnterior = this.posY;
        int novaPosX = this.posX;   
        int novaPosY = this.posY;

         switch (direcao) {
            case 1: this.movimento = Movimento.UP; novaPosY++;break;
            case 2: this.movimento = Movimento.DOWN; novaPosY--; break;
            case 3: this.movimento = Movimento.RIGHT; novaPosX++;break;
            case 4: this.movimento = Movimento.LEFT; novaPosX--;break;
            default: this.qtdMovimentosInvalidos++; throw new MovimentoInvalidoException("Movimento inexistente! O movimento escolhido não é existente.");
        }

        if(novaPosX <= 0 || novaPosX > Tabuleiro.DIMENSAO_TABULEIRO || novaPosY <= 0 || novaPosY > Tabuleiro.DIMENSAO_TABULEIRO){
            this.qtdMovimentosInvalidos++;
            throw new MovimentoInvalidoException("Movimento inválido! O robô não pode sair do tabuleiro.");
        }

        this.posX = novaPosX;
        this.posY = novaPosY;
        this.qtdMovimentosValidos++;
        System.out.println("Robô está na posição: [" + novaPosX + "," + novaPosY + "]");

    }

    public void voltarPosicaoAnterior(){

        this.posX = this.posXAnterior;
        this.posY = this.posYAnterior;
        System.out.println("Robô volta para a casa anterior:  [" + this.posX + "," + this.posY + "]");

    }
    
    public String toString(){
        int totalMovimentos = this.qtdMovimentosInvalidos + this.qtdMovimentosValidos;
        return "Robô " + this.cor.getTipoCor() + 
        "\nQuantidade de movimentos válidos: " + this.qtdMovimentosValidos + 
        "\nQuantidade de movimentos inválidos: " + this.qtdMovimentosInvalidos +
         "\nTotal de movimentos: " + totalMovimentos;
    }

    public boolean encontrouAlimento(Fruta fruta){

        if(fruta.getPosX() == this.posX && fruta.getPosY() == this.posY)
            return true;
        
        return false;
    }


    
}
