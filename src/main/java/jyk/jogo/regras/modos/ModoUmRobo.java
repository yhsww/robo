package jyk.jogo.regras.modos;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.MovimentoInvalidoException;
import jyk.jogo.regras.robo.Robo;

public class ModoUmRobo extends Modo {

    @Override
    public Resultado posicionarRobo(Posicao pos) {
        Robo novoRobo = new Robo(Cor.AMARELO);

        novoRobo.setPos(pos);
        if (tabuleiro.adicionarRobo(novoRobo)) {
            this.setFase(Fase.JOGANDO);
            return new Resultado(
                "Robô em (" + pos.x() + "," + pos.y() + ").",
                false
            );
        }

        return new Resultado(
            "Essa célula já está ocupada. Escolha outra.",
            false
        );
    }

    @Override
    public Resultado processar(String cmd) {
        Robo r = this.tabuleiro.getRobos().getFirst();

        Resultado resultado = null;
        try {
            resultado = r.mover(cmd);
        } catch (MovimentoInvalidoException e) {
            resultado = new Resultado(e.getMessage(), false);
        }

        if (r.encontrouAlimento(tabuleiro.getFruta())) {
            resultado = new Resultado(
                "Fim de jogo! O robô " + r.getCor() + " alcançou a fruta!\n" +
                    r.toString() +
                    "\n=============================",
                true
            );
            setFase(Fase.TERMINADA);
        }

        return resultado;
    }
}
