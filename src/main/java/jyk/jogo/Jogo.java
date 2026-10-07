package jyk.jogo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.Tabuleiro;
import jyk.jogo.regras.celulas.Bomba;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.modos.Modo;
import jyk.jogo.regras.modos.ModoUmBurroUmInteligenteObstaculos;
import jyk.jogo.regras.modos.ModoUmRobo;
import jyk.jogo.regras.robo.Robo;

public class Jogo extends Application {

    private static final int DIMENSAO = Tabuleiro.DIMENSAO;
    private static final double CELULA = 100;
    private static final double TAMANHO_TABULEIRO = DIMENSAO * CELULA;
    private static final long DURACAO_ANIMACAO_MS = 250;

    private static final double TAMANHO_ITEM = 64;
    private static final double TAMANHO_IMAGEM_ROBO = 48;
    private static final double RAIO_ROBO = 30;

    private static final Color COR_MOLDURA = Color.web("#3a4178");
    private static final Color COR_CELULA_CLARA = Color.web("#2b3159");
    private static final Color COR_CELULA_ESCURA = Color.web("#232849");
    private static final Color COR_GRADE = Color.rgb(255, 255, 255, 0.06);
    private static final Color COR_COORDENADA = Color.web("#6b7299");
    private static final Color COR_DESTAQUE = Color.web("#5865f2");

    private Modo modo;
    private Canvas canvas;
    private TextArea log;
    private TextField entrada;

    private int hoverColuna = -1;
    private int hoverLinha = -1;

    private final Map<String, Image> imagens = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        modo = new ModoUmBurroUmInteligenteObstaculos();

        BorderPane root = new BorderPane();
        root.setCenter(criarAreaDoTabuleiro());
        root.setRight(criarPainel());

        Scene scene = new Scene(root, 960, 680);
        scene.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(560);
        stage.setTitle("Robô");
        stage.show();
        entrada.requestFocus();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        }.start();
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

        Region destaque = new Region();
        destaque.getStyleClass().add("destaque");
        destaque.setMinHeight(4);
        destaque.setPrefHeight(4);
        destaque.setMaxHeight(4);
        destaque.setMaxWidth(48);

        Label subtitulo = new Label("Digite um comando e pressione Enter");
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

        VBox painel = new VBox(10, titulo, destaque, subtitulo, secao, log, new HBox(10, entrada, botao));
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

    private Image carregarImagem(String nome) {
        if (imagens.containsKey(nome)) return imagens.get(nome);

        Image imagem = null;
        InputStream arquivo = getClass().getResourceAsStream("/images/" + nome);
        if (arquivo != null) {
            imagem = new Image(arquivo);
        } else {
            System.err.println("Imagem não encontrada: /images/" + nome);
        }

        imagens.put(nome, imagem);
        return imagem;
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
        LinearGradient degrade = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#1b2044")), new Stop(1, Color.web("#0c0e1f")));
        gc.setFill(degrade);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void desenharTabuleiro(GraphicsContext gc) {
        double x0 = origemX();
        double y0 = origemY();

        gc.setFill(Color.rgb(0, 0, 0, 0.25));
        gc.fillRoundRect(x0 - 10, y0 - 4, TAMANHO_TABULEIRO + 20, TAMANHO_TABULEIRO + 20, 24, 24);

        gc.setFill(COR_MOLDURA);
        gc.fillRoundRect(x0 - 10, y0 - 10, TAMANHO_TABULEIRO + 20, TAMANHO_TABULEIRO + 20, 24, 24);

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
        Image imagem = carregarImagem(nomeImagem);

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

            Image imagem = carregarImagem("robo.png");
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
