package jyk.jogo.regras.robo;

public class MovimentoInvalidoException extends RuntimeException {

    public MovimentoInvalidoException() {
        super(
            "O movimento requisitado é inválido! O robô não pode sair do tabuleiro."
        );
    }
}
