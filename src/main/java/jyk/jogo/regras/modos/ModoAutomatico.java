package jyk.jogo.regras.modos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.robo.MovimentoInvalidoException;
import jyk.jogo.regras.robo.Robo;

public abstract class ModoAutomatico extends Modo {

    public static final long INTERVALO_MS = 500;

    protected final List<Robo> vencedores = new ArrayList<>();
    protected final List<Robo> mortos = new ArrayList<>();

    private int indiceVez = 0;

    @Override
    public boolean isAutomatico() {
        return true;
    }

    @Override
    public Resultado processar(String cmd) {
        if (getFase() != Fase.JOGANDO) return new Resultado("", true);

        StringBuilder sb = new StringBuilder();
        List<Robo> rbs = tabuleiro.getRobos();

        if (!rbs.isEmpty()) {
            indiceVez = indiceVez % rbs.size();
            Robo r = rbs.get(indiceVez);

            boolean saiuDoJogo = jogarVez(r, sb);

            if (!saiuDoJogo) indiceVez++;
        }

        if (!tabuleiro.getRobos().isEmpty()) {
            return new Resultado(sb.toString().trim(), false);
        }

        sb.append("\n").append(mensagemFinal()).append("\n");
        for (Robo r : vencedores) sb.append(r.toString()).append("\n");
        for (Robo r : mortos) sb.append(r.toString()).append("\n");
        sb.append("=============================");

        return new Resultado(sb.toString().trim(), true);
    }

    private boolean jogarVez(Robo r, StringBuilder sb) {
        try {
            int dir = ThreadLocalRandom.current().nextInt(1, 5);
            r.mover(dir);
        } catch (MovimentoInvalidoException e) {
            sb.append(r.getCor() + " tentou fugir!\n");
            return false;
        }

        for (Obstaculo o : tabuleiro.getObstaculos()) {
            if (!r.getPos().equals(o.getPos())) continue;

            sb.append(o.bater(r).mensagem() + "\n");

            if (r.getExplodiu()) {
                tabuleiro.removerRobo(r);
                tabuleiro.removerObstaculo(o);
                mortos.add(r);
                return true;
            }
            break;
        }

        if (r.encontrouAlimento(tabuleiro.getFruta())) {
            sb.append(r.getCor() + " encontrou a fruta!\n");
            tabuleiro.removerRobo(r);
            vencedores.add(r);
            return true;
        }

        sb.append(r.getCor() + " para " + r.getPos() + "\n");
        return false;
    }

    protected String mensagemFinal() {
        if (mortos.isEmpty()) {
            return (
                "Fim de jogo! Ambos os robôs alcançaram a fruta! O robô " +
                vencedores.get(0).getCor() +
                " chegou primeiro."
            );
        }

        if (vencedores.isEmpty()) {
            return "Fim de jogo! Ambos os robôs explodiram!";
        }

        return (
            "Fim de jogo! O robô " +
            vencedores.get(0).getCor() +
            " alcançou a fruta e o robô " +
            mortos.get(0).getCor() +
            " explodiu!"
        );
    }
}
