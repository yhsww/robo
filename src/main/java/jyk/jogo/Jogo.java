package jyk.jogo;

import java.util.function.Supplier;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jyk.jogo.regras.modos.Modo;

public class Jogo extends Application {

    private Stage stage;
    private TelaJogo telaAtual;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Scene scene = new Scene(new Pane(), 960, 680);
        scene.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(640);
        stage.setTitle("Robô");

        mostrarMenu();
        stage.show();
    }

    private void mostrarMenu() {
        if (telaAtual != null) {
            telaAtual.parar();
            telaAtual = null;
        }
        stage.getScene().setRoot(new TelaMenu(this::iniciarJogo).criar());
    }

    private void iniciarJogo(Supplier<Modo> criadorDoModo) {
        telaAtual = new TelaJogo(criadorDoModo.get(), this::mostrarMenu);
        stage.getScene().setRoot(telaAtual.criar());
    }
}
