package jyk.jogo.regras.robo;

public enum Cor {
    VERMELHO,
    AZUL,
    VERDE,
    BRANCO,
    PRETO,
    AMARELO;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
