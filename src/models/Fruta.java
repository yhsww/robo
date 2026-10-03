package models;

public class Fruta {

    private int posX;
    private int posY;

    public Fruta(){
        this.posX = 0;
        this.posY = 0;
    }

    public void setPosX(int posX){
        this.posX = posX;
    }

    public void setPosY(int posY){
        this.posY = posY;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }
    
    
}
