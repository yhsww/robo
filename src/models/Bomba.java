package models;

public class Bomba extends Obstaculo{

    public Bomba(int id){
        super(id);
    }

    public void bater(Robo01 robo){
        robo.setPodeseMover(false);
    }
    
}
