package jyk.jogo.regras;

import java.util.ArrayList;
import java.util.List;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.robo.Robo;

public class Tabuleiro {

    public static final int DIMENSAO = 4;
    private List<Obstaculo> obstaculos;
    private List<Robo> robos;
    private Fruta fruta;

    public Tabuleiro() {
        this.obstaculos = new ArrayList<>();
        this.robos = new ArrayList<>();
    }

    public boolean validarPosicao(Posicao pos) {
        boolean naFruta = fruta != null && pos.equals(fruta.getPos());
        boolean outroRobo = robos.stream().anyMatch(r -> r.getPos().equals(pos));
        boolean obstaculo = obstaculos.stream().anyMatch(o -> o.getPos().equals(pos));

        return !naFruta && !outroRobo && !obstaculo;
    }

    public boolean adicionarFruta(Fruta fruta) {
        if (validarPosicao(fruta.getPos())) {
            this.fruta = fruta;
            return true;
        }

        return false;
    }

    public boolean adicionarObstaculos(Obstaculo obstaculo) {
        if (validarPosicao(obstaculo.getPos())) {
            this.obstaculos.add(obstaculo);
            return true;
        }

        return false;
    }

    public boolean adicionarRobo(Robo robo) {
        if (validarPosicao(robo.getPos())) {
            this.robos.add(robo);
            return true;
        }

        return false;
    }

    public Fruta getFruta() {
        return fruta;
    }

    public List<Robo> getRobos() {
        return List.copyOf(robos);
    }

    public void removerRobo(Robo r) {
        robos.remove(r);
    }

    public List<Obstaculo> getObstaculos() {
        return List.copyOf(obstaculos);
    }

    public void removerObstaculo(Obstaculo o) {
        obstaculos.remove(o);
    }
}
