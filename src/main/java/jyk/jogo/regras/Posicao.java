package jyk.jogo.regras;

public record Posicao(int x, int y) {
    @Override
    public String toString() {
        return "(" + this.x() + ", " + this.y() + ")";
    }
}
