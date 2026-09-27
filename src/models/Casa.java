package models;

public class Casa {

    private int posX;
    private int posY;

    public Casa(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
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
