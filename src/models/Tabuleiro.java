package models;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

    public static int DIMENSAO_TABULEIRO = 4;
    private static List<Casa> posicoesTabuleiro;

    public Tabuleiro(){
        Tabuleiro.posicoesTabuleiro = new ArrayList<>(Tabuleiro.DIMENSAO_TABULEIRO*Tabuleiro.DIMENSAO_TABULEIRO);
    }

    public void criarTabuleiro(){

        Tabuleiro.posicoesTabuleiro.clear();
        
        //4X4 = 16

        int posX = 0;

        while(posX < Tabuleiro.DIMENSAO_TABULEIRO){

            for(Casa casa: Tabuleiro.posicoesTabuleiro){
                casa.setPosX(posX);
                for(int posY = 0; posY < Tabuleiro.DIMENSAO_TABULEIRO; posY++){
                    casa.setPosY(posY);
                }
                
                posX++;
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
