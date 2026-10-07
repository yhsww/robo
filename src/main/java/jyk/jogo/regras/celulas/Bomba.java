package jyk.jogo.regras.celulas;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.modos.Modo.Resultado;
import jyk.jogo.regras.robo.Robo;

public class Bomba extends Obstaculo {

    public Bomba(int id, Posicao pos) {
        super(id, pos);
    }

    @Override
    public Resultado bater(Robo robo) {
        robo.setExplodiu(true);

        return new Resultado("BOOM! O robô " + robo.getCor() + " explodiu!", false);
    }
}
