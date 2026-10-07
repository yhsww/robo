package jyk.jogo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jyk.jogo.regras.Posicao;
import jyk.jogo.regras.Tabuleiro;
import jyk.jogo.regras.celulas.Bomba;
import jyk.jogo.regras.celulas.Fruta;
import jyk.jogo.regras.celulas.Obstaculo;
import jyk.jogo.regras.celulas.Rocha;
import jyk.jogo.regras.modos.Modo;
import jyk.jogo.regras.modos.ModoUmBurroUmInteligenteUmaFrutaEObstaculos;
import jyk.jogo.regras.robo.Robo;

public class Jogo extends Application {

    private static final int TAMANHO_TABULEIRO = Tabuleiro.DIMENSAO;
    private static final double TAMANHO_CELULA = 100;
    private static final long DURACAO_ANIMACAO_MS = 250;

    public static void main(String[] args) {
        launch(args);
    }

    private Modo modo;
    private Canvas canvas;
    private TextArea log;

    private TextField entrada;

    @Override
    public void start(Stage stage) {
        modo = new ModoUmBurroUmInteligenteUmaFrutaEObstaculos();

        canvas = new Canvas(600, 600);
        Pane centro = new Pane(canvas);
        canvas.widthProperty().bind(centro.widthProperty());
        canvas.heightProperty().bind(centro.heightProperty());
        canvas.widthProperty().addListener(e -> draw());
        canvas.heightProperty().addListener(e -> draw());
        canvas.setOnMouseClicked(e -> cliqueNaCelula(e.getX(), e.getY()));

        Label titulo = new Label("Comandos");
        titulo.setStyle("-frutaPixelX-font-weight: bold; -frutaPixelX-font-tamanhoTabuleiro: 14px;");

        log = new TextArea();
        log.setEditable(false);
        log.setWrapText(true);
        VBox.setVgrow(log, Priority.ALWAYS);

        entrada = new TextField();
        HBox.setHgrow(entrada, Priority.ALWAYS);
        entrada.setOnAction(e -> enviar());

        Button btao = new Button("Enviar");
        btao.setOnAction(e -> enviar());

        VBox painel = new VBox(8, titulo, log, new HBox(6, entrada, btao));
        painel.setPadding(new Insets(10));
        painel.setPrefWidth(300);
        painel.setMinWidth(300);
        painel.setStyle("-frutaPixelX-background-color: #f2f2f2;");

        BorderPane root = new BorderPane();
        root.setCenter(centro);
        root.setRight(painel);

        stage.setScene(new Scene(root, 880, 600));
        stage.setTitle("Robô");
        stage.show();
        entrada.requestFocus();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        }.start();

        draw();
    }

    private void enviar() {
        String texto = entrada.getText().trim();
        entrada.clear();
        if (texto.isEmpty()) return;

        escrever("> " + texto);

        Modo.Resultado r = modo.executar(texto);
        escrever(r.mensagem());
        draw();

        if (r.terminou()) {
            entrada.setDisable(true);
        } else {
            entrada.requestFocus();
        }
    }

    private void escrever(String linha) {
        log.appendText(linha + "\n");
    }

    private void cliqueNaCelula(double mouseX, double mouseY) {
        if (modo.getFase() != Modo.Fase.POSICIONAR_FRUTA) return;

        double origemX = originX();
        double origemY = originY();
        double tamanhoTabuleiro = TAMANHO_TABULEIRO * TAMANHO_CELULA;

        // retorna caso clique fora da grid
        if (mouseX < origemX || mouseY < origemY || mouseX >= origemX + tamanhoTabuleiro || mouseY >= origemY + tamanhoTabuleiro) return;

        int coluna = (int) ((mouseX - origemX) / TAMANHO_CELULA);
        int linha = TAMANHO_TABULEIRO - 1 - (int) ((mouseY - origemY) / TAMANHO_CELULA);

        Modo.Resultado r = modo.clicar(new Posicao(coluna, linha));
        escrever(r.mensagem());
        draw();
    }

    private void draw() {
        GraphicsContext contextoGrafico = canvas.getGraphicsContext2D();
        double largura = canvas.getWidth();
        double altura = canvas.getHeight();

        contextoGrafico.setFill(Color.WHITE);
        contextoGrafico.fillRect(0, 0, largura, altura);

        double tamanhoTabuleiro = TAMANHO_TABULEIRO * TAMANHO_CELULA;
        double origemX = originX();
        double origemY = originY();

        contextoGrafico.setStroke(Color.GRAY);
        contextoGrafico.setLineWidth(1);
        for (int i = 0; i <= TAMANHO_TABULEIRO; i++) {
            contextoGrafico.strokeLine(origemX + i * TAMANHO_CELULA, origemY, origemX + i * TAMANHO_CELULA, origemY + tamanhoTabuleiro);
            contextoGrafico.strokeLine(origemX, origemY + i * TAMANHO_CELULA, origemX + tamanhoTabuleiro, origemY + i * TAMANHO_CELULA);
        }

        contextoGrafico.setStroke(Color.BLACK);
        contextoGrafico.setLineWidth(2);
        contextoGrafico.strokeRect(origemX, origemY, tamanhoTabuleiro, tamanhoTabuleiro);

        Fruta fruta = modo.getFruta();
        if (fruta != null) {
            double frutaPixelX = origemX + fruta.getPos().x() * TAMANHO_CELULA + TAMANHO_CELULA / 2;
            double frutaPixelY = origemY + tamanhoTabuleiro - (fruta.getPos().y() * TAMANHO_CELULA + TAMANHO_CELULA / 2);

            Color corJavaFX = switch (fruta.getTipo()) {
                case BANANA -> Color.YELLOW;
                case UVA -> Color.PURPLE;
                case MACA -> Color.RED;
                case LARANJA -> Color.ORANGE;
            };
            contextoGrafico.setFill(corJavaFX);
            contextoGrafico.fillOval(frutaPixelX - 20, frutaPixelY - 20, 40, 40);
        }

        for (Obstaculo o : modo.getObstaculos()) {
            double pixelX = origemX + o.getPos().x() * TAMANHO_CELULA + TAMANHO_CELULA / 2;
            double pixelY = origemY + tamanhoTabuleiro - (o.getPos().y() * TAMANHO_CELULA + TAMANHO_CELULA / 2);

            Color corObs = null;
            if (o instanceof Bomba) {
                corObs = Color.BLACK;
            } else if (o instanceof Rocha) {
                corObs = Color.GREY;
            }
            contextoGrafico.setFill(corObs);
            contextoGrafico.fillOval(pixelX - 30, pixelY - 30, 60, 60);
            contextoGrafico.setStroke(Color.BLACK);
            contextoGrafico.setLineWidth(2);
            contextoGrafico.strokeOval(pixelX - 30, pixelY - 30, 60, 60);
        }

        for (Robo r : modo.getRobos()) {
            if (!r.getExplodiu()) {
                double interpX, interpY;
                long agora = System.currentTimeMillis();
                long antes = agora - r.getUltimoMovimento();
                if (antes < 0 || antes > DURACAO_ANIMACAO_MS || r.getPos().equals(r.getPosAnterior())) {
                    interpX = r.getPos().x();
                    interpY = r.getPos().y();
                } else {
                    double t = antes / (double) DURACAO_ANIMACAO_MS;
                    double inicioX = r.getPosAnterior().x();
                    double inicioY = r.getPosAnterior().y();
                    double fimX = r.getPos().x();
                    double fimY = r.getPos().y();
                    interpX = inicioX + (fimX - inicioX) * t;
                    interpY = inicioY + (fimY - inicioY) * t;
                }
                double pixelX = origemX + interpX * TAMANHO_CELULA + TAMANHO_CELULA / 2;
                double pixelY = origemY + tamanhoTabuleiro - (interpY * TAMANHO_CELULA + TAMANHO_CELULA / 2);

                Color cor = switch (r.getCor()) {
                    case VERMELHO -> Color.RED;
                    case AZUL -> Color.BLUE;
                    case VERDE -> Color.GREEN;
                    case BRANCO -> Color.WHITE;
                    case PRETO -> Color.BLACK;
                    case AMARELO -> Color.YELLOW;
                };
                contextoGrafico.setFill(cor);
                contextoGrafico.fillOval(pixelX - 30, pixelY - 30, 60, 60);
                contextoGrafico.setStroke(Color.BLACK);
                contextoGrafico.setLineWidth(2);
                contextoGrafico.strokeOval(pixelX - 30, pixelY - 30, 60, 60);
            }
        }
    }

    private double originX() {
        return (canvas.getWidth() - TAMANHO_TABULEIRO * TAMANHO_CELULA) / 2;
    }

    private double originY() {
        return (canvas.getHeight() - TAMANHO_TABULEIRO * TAMANHO_CELULA) / 2;
    }
}
