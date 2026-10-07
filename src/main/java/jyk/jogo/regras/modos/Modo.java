package jyk.jogo.regras.modos;

import java.util.List;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.Tabuleiro;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.robo.Robo;

public abstract class Modo {

    public enum Fase {
        POSICIONAR_FRUTA,
        POSICIONAR_ROBO,
        POSICIONAR_OBSTACULO,
        JOGANDO,
        TERMINADA,
    }

    public record Resultado(String mensagem, boolean terminou) {}

    protected final Tabuleiro tabuleiro = new Tabuleiro();
    protected Fase fase = Fase.POSICIONAR_FRUTA;

    protected Resultado posicionarFruta(Posicao pos) {
        if (tabuleiro.adicionarFruta(new Fruta(pos, Fruta.Tipo.BANANA))) {
            fase = Fase.POSICIONAR_ROBO;
            return new Resultado(
                "Fruta em (" +
                    pos.x() +
                    "," +
                    pos.y() +
                    ").",
                false
            );
        }

        return new Resultado(
            "Essa célula já está ocupada. Escolha outra.",
            false
        );
    }

    protected abstract Resultado posicionarRobo(Posicao pos);

    protected Resultado posicionarObstaculo(Posicao pos) {
        return new Resultado("Este modo não posiciona obstáculos.", false);
    }

    public Resultado clicar(Posicao pos) {
        if (fase == Fase.POSICIONAR_FRUTA) {
            return posicionarFruta(pos);
        } else if (fase == Fase.POSICIONAR_ROBO) {
            return posicionarRobo(pos);
        } else if (fase == Fase.POSICIONAR_OBSTACULO) {
            return posicionarObstaculo(pos);
        }

        return new Resultado("Células já posicionadas", false);
    }

    public Resultado executar(String entrada) {
        if (fase == Fase.POSICIONAR_FRUTA) {
            return new Resultado(
                "Clique em uma célula para posicionar a fruta primeiro.",
                false
            );
        } else if (fase == Fase.POSICIONAR_ROBO) {
            return new Resultado(
                "Clique em uma célula para posicionar o robô primeiro.",
                false
            );
        } else if (fase == Fase.POSICIONAR_OBSTACULO) {
            return new Resultado(
                "Clique em uma célula para posicionar o obstáculo primeiro.",
                false
            );
        }

        Resultado r = processar(entrada.trim().toLowerCase());
        if (r.terminou()) fase = Fase.TERMINADA;
        return r;
    }

    public Fruta getFruta() {
        return tabuleiro.getFruta();
    }

    public List<Robo> getRobos() {
        return tabuleiro.getRobos();
    }

    public List<Obstaculo> getObstaculos() {
        return tabuleiro.getObstaculos();
    }

    public Fase getFase() {
        return fase;
    }

    protected abstract Resultado processar(String comando);

    public boolean isAutomatico() {
        return false;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }
}
