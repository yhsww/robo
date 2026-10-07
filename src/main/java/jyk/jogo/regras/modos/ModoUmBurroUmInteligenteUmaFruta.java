package jyk.jogo.regras.modos;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.robo.Cor;
import jyk.jogo.regras.robo.MovimentoInvalidoException;
import jyk.jogo.regras.robo.Robo;
import jyk.jogo.regras.robo.RoboInteligente;

public class ModoUmBurroUmInteligenteUmaFruta extends Modo {

    public ModoUmBurroUmInteligenteUmaFruta() {
        tabuleiro.adicionarRobo(new Robo(Cor.VERMELHO));

        Robo robo2 = new RoboInteligente(Cor.AZUL);
        robo2.setPos(new Posicao(3, 3));
        tabuleiro.adicionarRobo(robo2);
    }

    @Override
    public Resultado processar(String cmd) {
        if (getFase() != Fase.JOGANDO) return new Resultado("", true);

        List<Robo> rbs = this.tabuleiro.getRobos();
        StringBuilder sb = new StringBuilder();

        for (Robo r : rbs) {
            try {
                int dir = ThreadLocalRandom.current().nextInt(1, 5);
                r.mover(dir);
            } catch (MovimentoInvalidoException e) {
                sb.append(r.getCor() + " tentou fugir!\n");
                continue;
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
