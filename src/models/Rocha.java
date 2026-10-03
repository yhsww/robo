package models;

public class Rocha extends Obstaculo{

    public Rocha(int id){
        super(id);
    }

    public void bater(Robo robo){

        robo.voltarPosicaoAnterior();
        System.out.println("Ops! O robô encontrou uma rocha e voltou para a casa anterior.");
        
    }
    
}
