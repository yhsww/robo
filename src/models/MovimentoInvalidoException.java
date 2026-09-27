package models;

public class MovimentoInvalidoException extends RuntimeException{

    public MovimentoInvalidoException(String movimento){

        super(movimento + " é inválido! O robô não pode sair do tabuleiro.");
    }


    public MovimentoInvalidoException(){

        super("Movimento inválido! O robô não pode acessar a casa desejada.");
    }
    
}
