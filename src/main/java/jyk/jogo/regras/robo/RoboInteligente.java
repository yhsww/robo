package jyk.jogo.regras.robo;

import java.util.concurrent.ThreadLocalRandom;
import jyk.jogo.regras.modos.Modo.Resultado;

public class RoboInteligente extends Robo {

    public RoboInteligente(Cor cor) {
        super(cor);
    }

    @Override
    public Resultado mover(int direcao) throws MovimentoInvalidoException {
        try {
            int direcaoAleatoria = ThreadLocalRandom.current().nextInt(1, 5);
            return super.mover(direcaoAleatoria);
        } catch (MovimentoInvalidoException e) {
            int novaDirecaoAleatoria = ThreadLocalRandom.current().nextInt(1, 5);
            return this.mover(novaDirecaoAleatoria);
        }
    }
}
