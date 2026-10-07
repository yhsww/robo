package jyk.jogo.regras.modos;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.celulas.Bomba;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.celulas.Rocha;
import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.Robo;
import jyk.jogo.regras.robo.RoboInteligente;

public class ModoUmBurroUmInteligenteObstaculos extends ModoAutomatico {

    @Override
    public Resultado posicionarRobo(Posicao pos) {
        boolean segundoRobo = tabuleiro.getRobos().size() == 1;

        Robo novoRobo = segundoRobo
            ? new RoboInteligente(Cor.AZUL)
            : new Robo(Cor.VERMELHO);
        novoRobo.setPos(pos);

        if (!tabuleiro.adicionarRobo(novoRobo)) {
            return new Resultado(
                "Essa célula já está ocupada. Escolha outra.",
                false
            );
        }

        if (segundoRobo) {
            setFase(Fase.POSICIONAR_OBSTACULO);
            return new Resultado(
                "Robô em (" + pos.x() + "," + pos.y() + "). Agora posicione a rocha.",
                false
            );
        }

        return new Resultado(
            "Robô em (" + pos.x() + "," + pos.y() + ").",
            false
        );
    }

    @Override
    protected Resultado posicionarObstaculo(Posicao pos) {
        boolean primeiroObstaculo = tabuleiro.getObstaculos().isEmpty();

        Obstaculo novoObstaculo = primeiroObstaculo
            ? new Rocha(1, pos)
            : new Bomba(2, pos);

        if (!tabuleiro.adicionarObstaculos(novoObstaculo)) {
            return new Resultado(
                "Essa célula já está ocupada. Escolha outra.",
                false
            );
        }

        if (primeiroObstaculo) {
            return new Resultado(
                "Rocha em (" + pos.x() + "," + pos.y() + "). Agora posicione a bomba.",
                false
            );
        }

        setFase(Fase.JOGANDO);
        return new Resultado(
            "Bomba em (" + pos.x() + "," + pos.y() + ").",
            false
        );
    }
}
