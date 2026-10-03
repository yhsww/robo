package models;

import java.util.concurrent.ThreadLocalRandom;

public class RoboInteligente extends Robo{

    private int ultimaDirecaoInvalida = 0;

    public RoboInteligente(Cor cor){
        super(cor);
    }

    public void mover() throws MovimentoInvalidoException{

        int direcao = ThreadLocalRandom.current().nextInt(1, 5);

        while(this.ultimaDirecaoInvalida == direcao){
            direcao = ThreadLocalRandom.current().nextInt(1, 5);
        }

        try{

            mover(direcao);
            this.ultimaDirecaoInvalida = 0;

        }catch(MovimentoInvalidoException e){
            
            this.ultimaDirecaoInvalida = direcao;
            this.qtdMovimentosInvalidos++;
            throw e;
        }

        this.qtdMovimentosValidos++;
    }
    
}
