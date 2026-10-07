package jyk.jogo.regras.modos;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.celulas.Bomba;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.celulas.Rocha;
import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.MovimentoInvalidoException;
import jyk.jogo.regras.robo.Robo;
import jyk.jogo.regras.robo.RoboInteligente;

public class ModoUmBurroUmInteligenteObstaculos extends Modo {

    public ModoUmBurroUmInteligenteObstaculos() {
        Rocha rocha = new Rocha(1, new Posicao(0, 3));
        Bomba bomba = new Bomba(2, new Posicao(3, 0));

        tabuleiro.adicionarObstaculos(rocha);
        tabuleiro.adicionarObstaculos(bomba);
    }

    @Override
    public Resultado posicionarRobo(Posicao pos) {
        Robo novoRobo = null;

        if (tabuleiro.getRobos().size() == 1) {
            novoRobo = new RoboInteligente(Cor.AZUL);
            this.setFase(Fase.JOGANDO);
        } else {
            novoRobo = new Robo(Cor.VERMELHO);
        }

        novoRobo.setPos(pos);
        if (tabuleiro.adicionarRobo(novoRobo)) {
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
        if (getFase() != Fase.JOGANDO) return new Resultado("", true);

        List<Robo> rbs = this.tabuleiro.getRobos();
        List<Obstaculo> obs = this.tabuleiro.getObstaculos();
        StringBuilder sb = new StringBuilder();

        for (Robo r : rbs) {
            try {
                int dir = ThreadLocalRandom.current().nextInt(1, 5);
                r.mover(dir);
            } catch (MovimentoInvalidoException e) {
                sb.append(r.getCor() + " tentou fugir!\n");
                continue;
            }

            for (Obstaculo o : obs) {
                if (r.getPos().equals(o.getPos())) {
                    sb.append(o.bater(r).mensagem() + "\n");
                }

                if (r.getExplodiu()) {
                    tabuleiro.removerRobo(r);
                    tabuleiro.removerObstaculo(o);
                }
            }

            if (r.encontrouAlimento(tabuleiro.getFruta())) {
                sb.append(r.getCor() + " encontrou a fruta!\n");
                tabuleiro.removerRobo(r);
            } else {
                sb.append(r.getCor() + " para " + r.getPos() + "\n");
            }
        }

        return new Resultado(sb.toString().trim(), false);
    }
}
