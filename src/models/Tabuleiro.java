package models;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    public static final int DIMENSAO_TABULEIRO = 4;
    private List<Casa> posicoesTabuleiro;

    public Tabuleiro(){
        this.posicoesTabuleiro = new ArrayList<>(Tabuleiro.DIMENSAO_TABULEIRO*Tabuleiro.DIMENSAO_TABULEIRO);
    }

    public void criarTabuleiro(){

        this.posicoesTabuleiro.clear();
        
        //4X4 = 16

        for (int posX = 0; posX < Tabuleiro.DIMENSAO_TABULEIRO; posX++) {
            for (int posY = 0; posY < Tabuleiro.DIMENSAO_TABULEIRO; posY++) {
                
                // Instancia uma nova Casa informando a posição atual
                Casa novaCasa = new Casa(posX, posY);
                
                // Adiciona o objeto populado na lista do tabuleiro
                this.posicoesTabuleiro.add(novaCasa);
            }
        }

        //posicoesTabuleiro.get(0) -> pos = 1, 1
        //posicoesTabuleiro.get(3) -> pos = 1, 4
        //posicoesTabuleiro.get(12) -> pos = 4, 1
        //posicoesTabulerio.get(16) -> pos = 4, 4

        // l1 - 0 a 3
        // l2 - 4 a 7
        // l3 - 8 a 11
        // l4 - 12 a 16
    }

    
}
