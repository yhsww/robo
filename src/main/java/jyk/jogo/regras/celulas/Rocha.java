package jyk.jogo.regras.celulas;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.modos.Modo.Resultado;
import jyk.jogo.regras.robo.Robo;

public class Rocha extends Obstaculo {

    public Rocha(int id, Posicao pos) {
        super(id, pos);
    }

    @Override
    public Resultado bater(Robo robo) {
        robo.voltarPosicaoAnterior();

        return new Resultado("Ops! O robô encontrou uma rocha.", false);
    }
}
