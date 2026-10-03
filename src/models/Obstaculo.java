package models;

public abstract class Obstaculo {
    
    protected int id;
    protected int posX;
    protected int posY;
    
    public Obstaculo(int id){
        this.id = id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
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

    public abstract void bater(Robo robo);

}
