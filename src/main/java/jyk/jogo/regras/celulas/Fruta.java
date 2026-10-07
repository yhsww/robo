package jyk.jogo.regras.celulas;

import jyk.jogo.regras.Posicao;

public class Fruta {

    public enum Tipo {
        BANANA,
        UVA,
        MACA,
        LARANJA,
    }

    private Posicao pos;
    private Tipo tipo;

    public Fruta(Posicao pos, Tipo tipo) {
        this.pos = pos;
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Posicao getPos() {
        return pos;
    }

    public void setPos(Posicao pos) {
        this.pos = pos;
    }
}
