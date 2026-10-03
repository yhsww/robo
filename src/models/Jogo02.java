package models;


public class Jogo02 extends Jogo{

    public Jogo02(){
        super();
    }

    public void iniciarPartida(){

        if(!autorizarPartida()){
            return;
        }

        Robo vencedor = null;
        int partida = 1;

        while(vencedor == null){
            System.out.println();
            System.out.println("PARTIDA " + partida);
            pausa();   
            this.tabuleiro.criarTabuleiro();

            for(Robo robo : this.robos){

                moverAleatorio(robo);

                if(robo.encontrouAlimento(this.fruta)){
                    vencedor = robo;
                    break;
                }

            }  

             pausa();   
             this.tabuleiro.criarTabuleiro();
            partida++;
        }

       
        for(Robo robo: this.robos){
            System.out.println("------------");
            System.out.println(robo.toString());
        }

        System.out.println("Vencedor: robô " + vencedor.getCor().getTipoCor());

    }
    
}
