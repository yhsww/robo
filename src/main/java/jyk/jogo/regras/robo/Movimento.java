package jyk.jogo.regras.robo;

public enum Movimento {
    CIMA(1, 0, 1),
    BAIXO(2, 0, -1),
    DIREITA(3, 1, 0),
    ESQUERDA(4, -1, 0);

    public final int codigo;
    public final int dx;
    public final int dy;

    Movimento(int codigo, int dx, int dy) {
        this.codigo = codigo;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
