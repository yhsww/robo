package jyk.jogo.regras.robo;

import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.Tabuleiro;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.modos.Modo.Resultado;

public class Robo {

    protected Posicao pos;
    protected Posicao posAnterior;
    protected Movimento movimento;
    protected boolean explodiu;
    protected int qtdMovimentosValidos;
    protected int qtdMovimentosInvalidos;
    protected Cor cor;
    protected long ultimoMovimento = 0;

    public Robo(Cor cor) {
        this.cor = cor;
        this.pos = new Posicao(0, 0);
        this.posAnterior = new Posicao(0, 0);
        this.qtdMovimentosInvalidos = 0;
        this.qtdMovimentosValidos = 0;
        this.explodiu = false;
    }

    public Resultado mover(int direcao) throws MovimentoInvalidoException {
        Movimento mov = switch (direcao) {
            case 1 -> Movimento.CIMA;
            case 2 -> Movimento.BAIXO;
            case 3 -> Movimento.DIREITA;
            case 4 -> Movimento.ESQUERDA;
            default -> null;
        };

        if (mov == null) return new Resultado(
            "Direção " + direcao + " inválida",
            false
        );

        this.movimento = mov;

        Posicao novaPos = new Posicao(
            this.pos.x() + mov.dx,
            this.pos.y() + mov.dy
        );

        if (foraDoTabuleiro(novaPos)) {
            this.qtdMovimentosInvalidos++;
            throw new MovimentoInvalidoException();
        }

        this.posAnterior = this.pos;
        this.pos = new Posicao(novaPos.x(), novaPos.y());
        this.qtdMovimentosValidos++;
        this.ultimoMovimento = System.currentTimeMillis();

        return new Resultado(
            "Direção: " +
                mov +
                "\nRobô está na posição: [" +
                novaPos.x() +
                "," +
                novaPos.y() +
                "]",
            false
        );
    }

    public Resultado mover(String direcao) throws MovimentoInvalidoException {
        if (direcao.matches("[1-4]")) {
            return mover(Integer.parseInt(direcao));
        }

        Movimento mov = switch (direcao) {
            case "up", "cima" -> Movimento.CIMA;
            case "down", "baixo" -> Movimento.BAIXO;
            case "right", "direita" -> Movimento.DIREITA;
            case "left", "esquerda" -> Movimento.ESQUERDA;
            default -> null;
        };

        return mover(mov.codigo);
    }

    private boolean foraDoTabuleiro(Posicao pos) {
        return (
            pos.x() < 0 ||
            pos.x() >= Tabuleiro.DIMENSAO ||
            pos.y() < 0 ||
            pos.y() >= Tabuleiro.DIMENSAO
        );
    }

    public void voltarPosicaoAnterior() {
        this.pos = this.posAnterior;
    }

    public boolean encontrouAlimento(Fruta fruta) {
        return this.pos.equals(fruta.getPos());
    }

    @Override
    public String toString() {
        int totalMovimentos =
            this.qtdMovimentosInvalidos + this.qtdMovimentosValidos;
        return (
            "=============================" +
            "\nRobô " +
            this.cor +
            "\nQuantidade de movimentos válidos: " +
            this.qtdMovimentosValidos +
            "\nQuantidade de movimentos inválidos: " +
            this.qtdMovimentosInvalidos +
            "\nTotal de movimentos: " +
            totalMovimentos
        );
    }

    public Posicao getPos() {
        return pos;
    }

    public void setPos(Posicao pos) {
        this.pos = pos;
    }

    public Posicao getPosAnterior() {
        return posAnterior;
    }

    public void setPosAnterior(Posicao posAnterior) {
        this.posAnterior = posAnterior;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public boolean getExplodiu() {
        return explodiu;
    }

    public void setExplodiu(boolean explodiu) {
        this.explodiu = explodiu;
    }

    public int getQtdMovimentosValidos() {
        return qtdMovimentosValidos;
    }

    public void setQtdMovimentosValidos(int v) {
        this.qtdMovimentosValidos = v;
    }

    public int getQtdMovimentosInvalidos() {
        return qtdMovimentosInvalidos;
    }

    public void setQtdMovimentosInvalidos(int v) {
        this.qtdMovimentosInvalidos = v;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public long getUltimoMovimento() {
        return ultimoMovimento;
    }

    public void setUltimoMovimento(long ultimoMovimentoTempo) {
        this.ultimoMovimento = ultimoMovimentoTempo;
    }
}
