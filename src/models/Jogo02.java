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


        System.out.println("Preparando o tabuleiro...");
        pausa();
        this.tabuleiro.criarTabuleiro();
        System.out.println("Comece o jogo!");   

        while(vencedor == null){
            System.out.println();
            System.out.println("PARTIDA " + partida);

            for(Robo robo : this.robos){

                System.out.println();
                System.out.println("Robô - " + robo.getCor().getTipoCor());
                moverAleatorio(robo);

                if(robo.encontrouAlimento(this.fruta)){
                    vencedor = robo;
                    break;
                }

                pausa();   
                this.tabuleiro.criarTabuleiro();
                

            }  

            partida++;
        }

        pausa();   
        this.tabuleiro.criarTabuleiro();

       
        for(Robo robo: this.robos){
            System.out.println("------------");
            System.out.println(robo.toString());
        }

        System.out.println("Vencedor: robô " + vencedor.getCor().getTipoCor());

    }
    
}
