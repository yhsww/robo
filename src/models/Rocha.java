package models;

public class Rocha extends Obstaculo{

    public Rocha(int id){
        super(id);
    }

    public void bater(Robo01 robo){


        int posX = robo.getPosX();
        int posY = robo.getPosY();

        if(robo.getMovimento().equals(Movimento.UP)){
            posY -= 1;
            robo.setPosY(posY);
        }else if(robo.getMovimento().equals(Movimento.DOWN)){
            posY += 1;
            robo.setPosY(posY);
        }else if(robo.getMovimento().equals(Movimento.RIGHT)){
            posX -=1;
            robo.setPosX(posX);
        }else if(robo.getMovimento().equals(Movimento.LEFT)){
            posX += 1;
            robo.setPosX(posX);
        }


        
    }
    
}
