package jyk.jogo.regras.robo;

import java.util.concurrent.ThreadLocalRandom;
import jyk.jogo.regras.modos.Modo.Resultado;

public class RoboInteligente extends Robo {

    public RoboInteligente(Cor cor) {
        super(cor);
    }

    @Override
    public Resultado mover(int direcao) {
        while (true) {
            try {
                return super.mover(ThreadLocalRandom.current().nextInt(1, 5));
            } catch (MovimentoInvalidoException e) {
                qtdMovimentosInvalidos--;
            }
        }
    }
}
