package jyk.jogo.regras.modos;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.Robo;

public class ModoDoisRobosBurros extends ModoAutomatico {

    @Override
    public Resultado posicionarRobo(Posicao pos) {
        boolean segundoRobo = tabuleiro.getRobos().size() == 1;

        Robo novoRobo = new Robo(segundoRobo ? Cor.BRANCO : Cor.PRETO);
        novoRobo.setPos(pos);

        if (!tabuleiro.adicionarRobo(novoRobo)) {
            return new Resultado(
                "Essa célula já está ocupada. Escolha outra.",
                false
            );
        }

        if (segundoRobo) setFase(Fase.JOGANDO);

        return new Resultado(
            "Robô em (" + pos.x() + "," + pos.y() + ").",
            false
        );
    }
}
