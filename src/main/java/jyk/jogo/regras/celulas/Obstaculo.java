package jyk.jogo.regras.celulas;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.modos.Modo.Resultado;
import jyk.jogo.regras.robo.Robo;

public abstract class Obstaculo {

    protected int id;
    protected Posicao pos;

    public Obstaculo(int id, Posicao pos) {
        this.pos = pos;
        this.id = id;
    }

    public Posicao getPos() {
        return pos;
    }

    public void setPos(Posicao pos) {
        this.pos = pos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public abstract Resultado bater(Robo robo);
}
