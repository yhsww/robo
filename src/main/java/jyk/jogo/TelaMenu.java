package jyk.jogo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jyk.jogo.regras.modos.Modo;
import jyk.jogo.regras.modos.ModoDoisRobosBurros;
import jyk.jogo.regras.modos.ModoUmBurroUmInteligente;
import jyk.jogo.regras.modos.ModoUmBurroUmInteligenteObstaculos;
import jyk.jogo.regras.modos.ModoUmRobo;

public class TelaMenu {

    private record OpcaoModo(String titulo, String[] icones, Supplier<Modo> criador) {
    }

    private static final List<OpcaoModo> OPCOES = List.of(
            new OpcaoModo("Um robô",
                    new String[] {"robo.png", "banana.png"},
                    ModoUmRobo::new),
            new OpcaoModo("Dois robôs burros",
                    new String[] {"robo.png", "robo.png", "banana.png"},
                    ModoDoisRobosBurros::new),
            new OpcaoModo("Duelo de robôs",
                    new String[] {"robo.png", "robo.png", "banana.png"},
                    ModoUmBurroUmInteligente::new),
            new OpcaoModo("Duelo com obstáculos",
                    new String[] {"robo.png", "robo.png", "bomba.png", "rocha.png"},
                    ModoUmBurroUmInteligenteObstaculos::new));

    private final Consumer<Supplier<Modo>> aoEscolher;

    public TelaMenu(Consumer<Supplier<Modo>> aoEscolher) {
        this.aoEscolher = aoEscolher;
    }

    public Parent criar() {
        Label titulo = new Label("Robô");
        titulo.getStyleClass().add("menu-titulo");

        Label subtitulo = new Label("Escolha o modo de jogo");
        subtitulo.getStyleClass().add("menu-subtitulo");

        GridPane grade = new GridPane();
        grade.setHgap(20);
        grade.setVgap(20);
        grade.setAlignment(Pos.CENTER);
        grade.setPadding(new Insets(18, 0, 0, 0));

        for (int i = 0; i < OPCOES.size(); i++) {
            grade.add(criarCard(OPCOES.get(i)), i % 2, i / 2);
        }

        VBox conteudo = new VBox(10, titulo, subtitulo, grade);
        conteudo.getStyleClass().add("menu");
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setPadding(new Insets(24));
        return conteudo;
    }

    private VBox criarCard(OpcaoModo opcao) {
        HBox icones = new HBox(8);
        for (String nome : opcao.icones()) {
            Image imagem = Imagens.carregar(nome);
            if (imagem == null) continue;

            ImageView icone = new ImageView(imagem);
            icone.setFitWidth(36);
            icone.setFitHeight(36);
            icone.setPreserveRatio(true);
            icones.getChildren().add(icone);
        }
        icones.setMinHeight(36);

        icones.setAlignment(Pos.CENTER);

        Label titulo = new Label(opcao.titulo());
        titulo.getStyleClass().add("card-titulo");

        VBox card = new VBox(14, icones, titulo);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(240, 130);
        card.setMinSize(240, 130);
        card.setOnMouseClicked(e -> aoEscolher.accept(opcao.criador()));
        return card;
    }
}
