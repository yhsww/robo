package jyk.jogo.regras.modos;

import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.MovimentoInvalidoException;
import jyk.jogo.regras.robo.Robo;

public class ModoUmRoboUmaFruta extends Modo {

    public ModoUmRoboUmaFruta() {
        tabuleiro.adicionarRobo(new Robo(Cor.VERMELHO));
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
            resultado = new Resultado(r.toString(), true);
            setFase(Fase.TERMINADA);
        }

        return resultado;
    }
}
