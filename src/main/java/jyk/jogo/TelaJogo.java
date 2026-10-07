package jyk.jogo;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.Tabuleiro;
import jyk.jogo.regras.celulas.Bomba;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.modos.Modo;
import jyk.jogo.regras.modos.ModoAutomatico;
import jyk.jogo.regras.robo.Robo;

public class TelaJogo {

    private static final int DIMENSAO = Tabuleiro.DIMENSAO;
    private static final double CELULA = 100;
    private static final double TAMANHO_TABULEIRO = DIMENSAO * CELULA;
    private static final long DURACAO_ANIMACAO_MS = 250;

    private static final double TAMANHO_ITEM = 64;
    private static final double TAMANHO_IMAGEM_ROBO = 48;
    private static final double RAIO_ROBO = 30;

    private static final Color COR_FUNDO = Color.web("#1e1e1e");
    private static final Color COR_MOLDURA = Color.web("#3a3a3a");
    private static final Color COR_CELULA_CLARA = Color.web("#333333");
    private static final Color COR_CELULA_ESCURA = Color.web("#2b2b2b");
    private static final Color COR_GRADE = Color.rgb(255, 255, 255, 0.05);
    private static final Color COR_COORDENADA = Color.web("#8a8a8a");
    private static final Color COR_DESTAQUE = Color.web("#e0a526");

    private final Modo modo;
    private final Runnable aoVoltar;

    private Canvas canvas;
    private TextArea log;
    private TextField entrada;
    private AnimationTimer timer;
    private Timeline loopAutomatico;

    private int hoverColuna = -1;
    private int hoverLinha = -1;

    public TelaJogo(Modo modo, Runnable aoVoltar) {
        this.modo = modo;
        this.aoVoltar = aoVoltar;
    }

    public Parent criar() {
        BorderPane root = new BorderPane();
        root.setCenter(criarAreaDoTabuleiro());
        root.setRight(criarPainel());

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        };
        timer.start();

        Platform.runLater(() -> entrada.requestFocus());
        return root;
    }

    public void parar() {
        if (timer != null) timer.stop();
        pararLoopAutomatico();
    }

    private void iniciarLoopAutomatico() {
        if (loopAutomatico != null) return;

        escrever("Iniciando partida automática...");
        loopAutomatico = new Timeline(new KeyFrame(
            Duration.millis(ModoAutomatico.INTERVALO_MS),
            e -> executarTurnoAutomatico()
        ));
        loopAutomatico.setCycleCount(Timeline.INDEFINITE);
        loopAutomatico.play();
    }

    private void executarTurnoAutomatico() {
        Modo.Resultado resultado = modo.executar("");
        if (!resultado.mensagem().isEmpty()) escrever(resultado.mensagem());

        if (resultado.terminou()) pararLoopAutomatico();
    }

    private void pararLoopAutomatico() {
        if (loopAutomatico != null) loopAutomatico.stop();
    }

    private Pane criarAreaDoTabuleiro() {
        canvas = new Canvas(600, 600);
        Pane area = new Pane(canvas);
        canvas.widthProperty().bind(area.widthProperty());
        canvas.heightProperty().bind(area.heightProperty());

        canvas.setOnMouseClicked(e -> cliqueNaCelula(e.getX(), e.getY()));
        canvas.setOnMouseMoved(e -> {
            int[] celula = celulaEm(e.getX(), e.getY());
            hoverColuna = celula == null ? -1 : celula[0];
            hoverLinha = celula == null ? -1 : celula[1];
            boolean clicavel = celula != null && podeClicar();
            canvas.setCursor(clicavel ? Cursor.HAND : Cursor.DEFAULT);
        });
        canvas.setOnMouseExited(e -> {
            hoverColuna = -1;
            hoverLinha = -1;
            canvas.setCursor(Cursor.DEFAULT);
        });

        return area;
    }

    private VBox criarPainel() {
        Label titulo = new Label("Robô");
        titulo.getStyleClass().add("titulo");

        Label subtitulo = new Label(modo.isAutomatico()
            ? "Posicione as peças e assista à partida"
            : "Digite um comando e pressione Enter");
        subtitulo.getStyleClass().add("subtitulo");

        Label secao = new Label("HISTÓRICO");
        secao.getStyleClass().add("secao");
        secao.setPadding(new Insets(14, 0, 0, 0));

        log = new TextArea();
        log.getStyleClass().add("log");
        log.setEditable(false);
        log.setWrapText(true);
        VBox.setVgrow(log, Priority.ALWAYS);

        entrada = new TextField();
        entrada.getStyleClass().add("campo");
        entrada.setPromptText("Digite um comando...");
        entrada.setOnAction(e -> enviar());
        HBox.setHgrow(entrada, Priority.ALWAYS);

        Button botao = new Button("Enviar");
        botao.getStyleClass().add("botao");
        botao.setOnAction(e -> enviar());

        Button voltar = new Button("Voltar ao menu");
        voltar.getStyleClass().add("botao-secundario");
        voltar.setMaxWidth(Double.MAX_VALUE);
        voltar.setOnAction(e -> aoVoltar.run());

        HBox linhaEntrada = new HBox(10, entrada, botao);
        if (modo.isAutomatico()) {
            linhaEntrada.setVisible(false);
            linhaEntrada.setManaged(false);
        }

        VBox painel = new VBox(10, titulo, subtitulo, secao, log, linhaEntrada, voltar);
        painel.getStyleClass().add("painel");
        painel.setPadding(new Insets(22));
        painel.setPrefWidth(340);
        painel.setMinWidth(340);
        return painel;
    }

    private void enviar() {
        String texto = entrada.getText().trim();
        entrada.clear();
        if (texto.isEmpty()) return;

        escrever("> " + texto);

        Modo.Resultado resultado = modo.executar(texto);
        escrever(resultado.mensagem());

        if (resultado.terminou()) {
            entrada.setDisable(true);
        } else {
            entrada.requestFocus();
        }
    }

    private void escrever(String linha) {
        log.appendText(linha + "\n");
    }

    private boolean podeClicar() {
        return modo.getFase() != Modo.Fase.JOGANDO && modo.getFase() != Modo.Fase.TERMINADA;
    }

    private int[] celulaEm(double mouseX, double mouseY) {
        double x = mouseX - origemX();
        double y = mouseY - origemY();

        if (x < 0 || y < 0 || x >= TAMANHO_TABULEIRO || y >= TAMANHO_TABULEIRO) return null;

        int coluna = (int) (x / CELULA);
        int linha = DIMENSAO - 1 - (int) (y / CELULA);
        return new int[] {coluna, linha};
    }

    private void cliqueNaCelula(double mouseX, double mouseY) {
        if (!podeClicar()) return;

        int[] celula = celulaEm(mouseX, mouseY);
        if (celula == null) return;

        Modo.Resultado resultado = modo.clicar(new Posicao(celula[0], celula[1]));
        escrever(resultado.mensagem());

        if (modo.isAutomatico() && modo.getFase() == Modo.Fase.JOGANDO) {
            iniciarLoopAutomatico();
        }
    }

    private double origemX() {
        return (canvas.getWidth() - TAMANHO_TABULEIRO) / 2;
    }

    private double origemY() {
        return (canvas.getHeight() - TAMANHO_TABULEIRO) / 2;
    }

    private double centroX(double coluna) {
        return origemX() + coluna * CELULA + CELULA / 2;
    }

    private double centroY(double linha) {
        return origemY() + (DIMENSAO - 1 - linha) * CELULA + CELULA / 2;
    }

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        desenharFundo(gc);
        desenharTabuleiro(gc);
        desenharCoordenadas(gc);
        desenharFruta(gc);
        desenharObstaculos(gc);
        desenharRobos(gc);
    }

    private void desenharFundo(GraphicsContext gc) {
        gc.setFill(COR_FUNDO);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void desenharTabuleiro(GraphicsContext gc) {
        double x0 = origemX();
        double y0 = origemY();

        gc.setFill(COR_MOLDURA);
        gc.fillRect(x0 - 6, y0 - 6, TAMANHO_TABULEIRO + 12, TAMANHO_TABULEIRO + 12);

        for (int coluna = 0; coluna < DIMENSAO; coluna++) {
            for (int linha = 0; linha < DIMENSAO; linha++) {
                double x = centroX(coluna) - CELULA / 2;
                double y = centroY(linha) - CELULA / 2;

                gc.setFill((coluna + linha) % 2 == 0 ? COR_CELULA_ESCURA : COR_CELULA_CLARA);
                gc.fillRect(x, y, CELULA, CELULA);

                if (coluna == hoverColuna && linha == hoverLinha && podeClicar()) {
                    gc.setFill(Color.rgb(255, 255, 255, 0.12));
                    gc.fillRect(x, y, CELULA, CELULA);
                    gc.setStroke(COR_DESTAQUE);
                    gc.setLineWidth(2);
                    gc.strokeRect(x + 1, y + 1, CELULA - 2, CELULA - 2);
                }
            }
        }

        gc.setStroke(COR_GRADE);
        gc.setLineWidth(1);
        for (int i = 0; i <= DIMENSAO; i++) {
            gc.strokeLine(x0 + i * CELULA, y0, x0 + i * CELULA, y0 + TAMANHO_TABULEIRO);
            gc.strokeLine(x0, y0 + i * CELULA, x0 + TAMANHO_TABULEIRO, y0 + i * CELULA);
        }
    }

    private void desenharCoordenadas(GraphicsContext gc) {
        gc.setFont(Font.font("System", FontWeight.BOLD, 12));
        gc.setFill(COR_COORDENADA);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        for (int i = 0; i < DIMENSAO; i++) {
            gc.fillText(String.valueOf(i), centroX(i), origemY() + TAMANHO_TABULEIRO + 26);
            gc.fillText(String.valueOf(i), origemX() - 26, centroY(i));
        }
    }

    private void desenharFruta(GraphicsContext gc) {
        Fruta fruta = modo.getFruta();
        if (fruta == null) return;

        Color corReserva = switch (fruta.getTipo()) {
            case BANANA -> Color.YELLOW;
            case UVA -> Color.PURPLE;
            case MACA -> Color.RED;
            case LARANJA -> Color.ORANGE;
        };
        String imagem = switch (fruta.getTipo()) {
            case BANANA -> "banana.png";
            case UVA -> "uva.png";
            case MACA -> "maca.png";
            case LARANJA -> "laranja.png";
        };

        desenharItem(gc, imagem, centroX(fruta.getPos().x()), centroY(fruta.getPos().y()), corReserva);
    }

    private void desenharObstaculos(GraphicsContext gc) {
        for (Obstaculo obstaculo : modo.getObstaculos()) {
            boolean bomba = obstaculo instanceof Bomba;
            String imagem = bomba ? "bomba.png" : "rocha.png";
            Color corReserva = bomba ? Color.BLACK : Color.GREY;

            desenharItem(gc, imagem, centroX(obstaculo.getPos().x()), centroY(obstaculo.getPos().y()), corReserva);
        }
    }

    private void desenharItem(GraphicsContext gc, String nomeImagem, double x, double y, Color corReserva) {
        Image imagem = Imagens.carregar(nomeImagem);

        if (imagem == null) {
            gc.setFill(corReserva);
            gc.fillOval(x - 24, y - 24, 48, 48);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeOval(x - 24, y - 24, 48, 48);
            return;
        }

        gc.setFill(Color.rgb(0, 0, 0, 0.30));
        gc.fillOval(x - 22, y + TAMANHO_ITEM / 2 - 12, 44, 12);
        gc.drawImage(imagem, x - TAMANHO_ITEM / 2, y - TAMANHO_ITEM / 2, TAMANHO_ITEM, TAMANHO_ITEM);
    }

    private void desenharRobos(GraphicsContext gc) {
        for (Robo robo : modo.getRobos()) {
            if (robo.getExplodiu()) continue;

            double[] posicao = posicaoAnimada(robo);
            double x = centroX(posicao[0]);
            double y = centroY(posicao[1]);

            gc.setFill(Color.rgb(0, 0, 0, 0.35));
            gc.fillOval(x - 26, y + 22, 52, 14);

            gc.setFill(corDoRobo(robo));
            gc.fillOval(x - RAIO_ROBO, y - RAIO_ROBO, RAIO_ROBO * 2, RAIO_ROBO * 2);

            gc.setStroke(Color.web("#101322"));
            gc.setLineWidth(3);
            gc.strokeOval(x - RAIO_ROBO, y - RAIO_ROBO, RAIO_ROBO * 2, RAIO_ROBO * 2);

            Image imagem = Imagens.carregar("robo.png");
            if (imagem != null) {
                gc.drawImage(imagem, x - TAMANHO_IMAGEM_ROBO / 2, y - TAMANHO_IMAGEM_ROBO / 2,
                        TAMANHO_IMAGEM_ROBO, TAMANHO_IMAGEM_ROBO);
            }
        }
    }

    private double[] posicaoAnimada(Robo robo) {
        long decorrido = System.currentTimeMillis() - robo.getUltimoMovimento();
        Posicao atual = robo.getPos();
        Posicao anterior = robo.getPosAnterior();

        if (decorrido < 0 || decorrido > DURACAO_ANIMACAO_MS || atual.equals(anterior)) {
            return new double[] {atual.x(), atual.y()};
        }

        double progresso = decorrido / (double) DURACAO_ANIMACAO_MS;
        return new double[] {
            anterior.x() + (atual.x() - anterior.x()) * progresso,
            anterior.y() + (atual.y() - anterior.y()) * progresso
        };
    }

    private Color corDoRobo(Robo robo) {
        return switch (robo.getCor()) {
            case VERMELHO -> Color.RED;
            case AZUL -> Color.BLUE;
            case VERDE -> Color.GREEN;
            case BRANCO -> Color.WHITE;
            case PRETO -> Color.BLACK;
            case AMARELO -> Color.YELLOW;
        };
    }
}
